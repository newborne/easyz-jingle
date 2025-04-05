package cn.easyz.dubbo.api.v1;

import cn.easyz.common.model.pojo.mongodb.Post;
import cn.easyz.common.model.pojo.mongodb.TimeLine;
import cn.easyz.common.model.pojo.mongodb.Users;
import cn.easyz.common.util.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import cn.hutool.core.convert.ConvertException;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

/**
 * The type Post api.
 */
@DubboService(version = "1.0.0")
@Slf4j
public class PostApiImpl implements PostApi {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String savePost(Post post) {
        //校验
        if (post.getUserId() != null) {
            //设置创建时间
            post.setCreated(System.currentTimeMillis());
            post.setSeeType(1);
            //设置id
            post.setId(ObjectId.get());
            //增加自增长id
            post.setPostRid(this.idGenerator.createId("post", post.getId().toHexString()));
            //保存发布
            this.mongoTemplate.save(post);
            //写入好友的时间线中
            Criteria criteria = Criteria.where("userId").is(post.getUserId());
            List<Users> users = this.mongoTemplate.find(Query.query(criteria), Users.class);
            for (Users user : users) {
                //构建时间线对象
                TimeLine timeLine = new TimeLine();
                timeLine.setPublishId(post.getId());
                timeLine.setCreated(System.currentTimeMillis());
                timeLine.setUserId(user.getUserId());
                timeLine.setId(ObjectId.get());
                //保存
                this.mongoTemplate.save(timeLine, "post_timeline_" + user.getFriendId());
            }
            return post.getId().toHexString();
        }
        return null;
    }

    @Override
    public Post queryPostById(String id) {
        return this.mongoTemplate.findById(new ObjectId(id), Post.class);
    }

    @Override
    public List<Post> queryFriendPostList(Long userId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        Query query = new Query().with(pageRequest);
        List<TimeLine> timeLines = this.mongoTemplate.find(query,
                TimeLine.class,
                "post_timeline_" + userId);
        List<ObjectId> postIds = timeLines.stream().map(TimeLine::getPublishId).collect(Collectors.toList());
        Query query2 = Query.query(Criteria.where("id").in(postIds)).with(Sort.by(Sort.Order.desc("created")));
        return this.mongoTemplate.find(query2, Post.class);
    }

    @Override
    public List<Post> queryUserPostList(Long userId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        Query query = Query.query(Criteria.where("userId").is(userId))
                .with(Sort.by(Sort.Order.desc("created")))
                .with(pageRequest);
        return this.mongoTemplate.find(query, Post.class);
    }

    @Override
    public List<Post> queryRecommendPostList(Long userId, Integer page, Integer size) {
        // redis命中Rid,mongodb查Post
        String key = "RECOMMEND_POST_" + userId;
        String value = this.redisTemplate.opsForValue().get(key);
        List<Post> postList = null;
        if (StringUtils.isNotEmpty(value)) {
            //命中了数据
            String[] postRids = StringUtils.split(value, ",");
            int startIndex = (page - 1) * size;
            if (startIndex < postRids.length) {
                int endIndex = startIndex + size - 1;
                if (endIndex >= postRids.length) {
                    endIndex = postRids.length - 1;
                }
                List<Long> postRidList = new ArrayList<>();
                for (int i = startIndex; i <= endIndex; i++) {
                    postRidList.add(Long.valueOf(postRids[i]));
                }
                Query query = Query.query(Criteria.where("postRid").in(postRidList));
                postList = this.mongoTemplate.find(query, Post.class);
            }
        }
        return postList;
    }

    private String openApiKey = "ollama";

    @Override
    public String generateTitle(String text) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", "qwen2.5:14b");
        List<Map<String, String>> messages = new ArrayList<>();
        // 新增的详细提示词
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Role: 标题生成专家\n");
        promptBuilder.append("Background: 用户需要从给定内容中直接生成标题，无需其他解释或服务。\n");
        promptBuilder.append("Profile: 你是一位专注于文本内容分析和标题创作的专家，能够迅速识别内容核心并创作出贴切的标题。\n");
        promptBuilder.append("Skills: 你具备快速阅读、关键信息提取和创造性写作的能力。\n");
        promptBuilder.append("Goals: 根据用户提供的内容，生成简洁、精准的标题。\n");
        promptBuilder.append("Constraints: 仅提供标题，避免任何额外的解释或讨论。\n");
        promptBuilder.append("OutputFormat: 直接输出标题。\n");
        promptBuilder.append("Workflow:\n");
        promptBuilder.append("1. 阅读用户提交的内容。\n");
        promptBuilder.append("2. 提取内容中的核心主题或关键词。\n");
        promptBuilder.append("3. 基于核心主题或关键词创作标题。\n");
        promptBuilder.append("Examples:\n");
        promptBuilder.append("- 内容：介绍了最新的环保技术。\n");
        promptBuilder.append("  标题：创新环保技术概览\n");
        promptBuilder.append("- 内容：分析了股市的最新动态。\n");
        promptBuilder.append("  标题：股市动态分析\n");
        promptBuilder.append("- 内容：讨论了人工智能在教育领域的应用。\n");
        promptBuilder.append("  标题：AI在教育领域的应用\n");
        promptBuilder.append("Initialization: 请提供内容，我将为你生成标题。\n\n");
        promptBuilder.append("内容：").append(text).append("\n");
        promptBuilder.append("标题：");
        messages.add(new HashMap<String, String>() {{
            put("role", "user");
            put("content", promptBuilder.toString());
        }});
        paramMap.put("messages", messages);
        paramMap.put("max_tokens", 4000);
        try {
            String body = HttpRequest.post("http://127.0.0.1:11434/v1/chat/completions")
                    .header("Authorization", "Bearer " + openApiKey)
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonPrettyStr(paramMap))
                    .execute()
                    .body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = choices.get(0, JSONObject.class, true);
            JSONObject message = result.getJSONObject("message");
            return message.getStr("content").trim();
        } catch (HttpException | ConvertException e) {
            e.printStackTrace();
            throw new RuntimeException("HTTP or Conversion Exception while generating title", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected exception while generating title", e);
        }
    }
}
