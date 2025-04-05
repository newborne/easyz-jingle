package cn.easyz.server.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.easyz.common.model.enums.MsgEnum;
import cn.easyz.common.model.pojo.mongodb.Post;
import cn.easyz.common.model.pojo.mongodb.PostListenerMsg;
import cn.easyz.dubbo.api.v1.PostApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * The type Post listener.
 */
@Component
@RocketMQMessageListener(topic = "EASYZ_POST_TOPIC", consumerGroup = "POST_CONSUMER_GROUP")
public class PostListener implements RocketMQListener<String> {
    @DubboReference(version = "1.0.0")
    private PostApi postApi;
    @Autowired
    private MongoTemplate mongoTemplate;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Override
    public void onMessage(String s) {
        try {
            // 1 读取消息
            JsonNode jsonNode = MAPPER.readTree(s);
            Long userId = jsonNode.get("userId").asLong();
            MsgEnum type = MsgEnum.values()[jsonNode.get("type").asInt()];
            String publishId = jsonNode.get("publishId").asText();
            Long created = jsonNode.get("created").asLong();
            Long postRid = jsonNode.get("postRid").asLong();
            // 2 填充数据
            PostListenerMsg postListenerMsg = new PostListenerMsg();
            Post post = this.postApi.queryPostById(publishId);
            double rating = 0;
            int length = post.getText().length();
            int size = post.getMedias().size();
            // 2.1 填充rating
            switch (type) {
                case SAVE:
                    rating = length > 100 ? 20 : 10;
                    rating += size > 0 ? 5 * size : 0;
                    break;
                case LIKE:
                    rating = 10;
                    break;
                case DISLIKE:
                    rating = -10;
                    break;
                case COMMENT:
                    rating = 20;
                    break;
                case LOVE:
                    rating = 15;
                    break;
                case UNLOVE:
                    rating = -15;
                    break;
                case QUERY:
                    rating = 5;
                    break;
                default:
                    break;
            }
            postListenerMsg.setRating(rating);
            // 2.2 填充其他字段
            postListenerMsg.setUserId(userId);
            postListenerMsg.setCreated(created);
            postListenerMsg.setPostRid(postRid);
            // 3 存储数据
            this.mongoTemplate.save(postListenerMsg, "post_listener_msg");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
