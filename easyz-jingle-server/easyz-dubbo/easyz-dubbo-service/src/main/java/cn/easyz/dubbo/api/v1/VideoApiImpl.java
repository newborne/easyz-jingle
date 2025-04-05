package cn.easyz.dubbo.api.v1;

import cn.easyz.common.model.pojo.mongodb.TimeLine;
import cn.easyz.common.model.pojo.mongodb.Users;
import cn.easyz.common.model.pojo.mongodb.Video;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Video api.
 */
@DubboService(version = "1.0.0")
@Slf4j
public class VideoApiImpl implements VideoApi {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public String saveVideo(Video video) {
        //校验
        if (video.getUserId() != null) {
            //设置创建时间
            video.setCreated(System.currentTimeMillis());
            video.setSeeType(1);
            //设置id
            video.setId(ObjectId.get());
            //增加自增长id
            video.setVideoRid(this.idGenerator.createId("video", video.getId().toHexString()));
            //保存发布
            this.mongoTemplate.save(video);
            //写入好友的时间线中
            Criteria criteria = Criteria.where("userId").is(video.getUserId());
            List<Users> users = this.mongoTemplate.find(Query.query(criteria), Users.class);
            for (Users user : users) {
                //构建时间线对象
                TimeLine timeLine = new TimeLine();
                timeLine.setPublishId(video.getId());
                timeLine.setCreated(System.currentTimeMillis());
                timeLine.setUserId(user.getUserId());
                timeLine.setId(ObjectId.get());
                //保存
                this.mongoTemplate.save(timeLine, "video_timeline_" + user.getFriendId());
            }
            return video.getId().toHexString();
        }
        return null;
    }
    @Override
    public Video queryVideoById(String id) {
        return this.mongoTemplate.findById(new ObjectId(id), Video.class);
    }
    @Override
    public List<Video> queryFriendVideoList(Long userId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        Query query = new Query().with(pageRequest);
        List<TimeLine> timeLines = this.mongoTemplate.find(query,
                TimeLine.class,
                "video_timeline_" + userId);
        List<ObjectId> videoIds = timeLines.stream().map(TimeLine::getPublishId).collect(Collectors.toList());
        Query query2 = Query.query(Criteria.where("id").in(videoIds)).with(Sort.by(Sort.Order.desc("created")));
        return this.mongoTemplate.find(query2, Video.class);
    }
    @Override
    public List<Video> queryUserVideoList(Long userId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("created")));
        Query query = Query.query(Criteria.where("userId").is(userId))
                .with(Sort.by(Sort.Order.desc("created")))
                .with(pageRequest);
        return this.mongoTemplate.find(query, Video.class);
    }
    @Override
    public List<Video> queryRecommendVideoList(Long userId, Integer page, Integer size) {
        // redis命中Rid,mongodb查Video
        String key = "RECOMMEND_POST_" + userId;
        String value = this.redisTemplate.opsForValue().get(key);
        List<Video> videoList = null;
        if (StringUtils.isNotEmpty(value)) {
            //命中了数据
            String[] videoRids = StringUtils.split(value, ",");
            int startIndex = (page - 1) * size;
            if (startIndex < videoRids.length) {
                int endIndex = startIndex + size - 1;
                if (endIndex >= videoRids.length) {
                    endIndex = videoRids.length - 1;
                }
                List<Long> videoRidList = new ArrayList<>();
                for (int i = startIndex; i <= endIndex; i++) {
                    videoRidList.add(Long.valueOf(videoRids[i]));
                }
                Query query = Query.query(Criteria.where("videoRid").in(videoRidList));
                videoList = this.mongoTemplate.find(query, Video.class);
            }
        }
        return videoList;
    }
}
