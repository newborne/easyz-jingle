package cn.easyz.server.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.easyz.common.model.enums.MsgEnum;
import cn.easyz.common.model.pojo.mongodb.ArticleListenerMsg;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * The type Article listener.
 */
@Component
@RocketMQMessageListener(topic = "EASYZ_ARTICLE_TOPIC", consumerGroup = "ARTICLE_CONSUMER_GROUP")
public class ArticleListener implements RocketMQListener<String> {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void onMessage(String s) {
        System.out.println("类" + this.getClass().getName() + "中" + Thread.currentThread()
                .getStackTrace()[1].getMethodName() + "方法：" + s);
        try {
            // 1 读取消息
            JsonNode jsonNode = MAPPER.readTree(s);
            Long userId = jsonNode.get("userId").asLong();
            MsgEnum type = MsgEnum.values()[jsonNode.get("type").asInt()];
            Long created = jsonNode.get("created").asLong();
            Long articleRid = jsonNode.get("articleRid").asLong();
            // 2 填充数据
            ArticleListenerMsg articleListenerMsg = new ArticleListenerMsg();
            double rating = 0;
            // 2.1 填充rating
            switch (type) {
                case LIKE:
                    rating = 10;
                    break;
                case DISLIKE:
                    rating = -10;
                    break;
                case QUERY:
                    rating = 5;
                    break;
                default:
                    break;
            }
            articleListenerMsg.setRating(rating);
            // 2.2 填充其他字段
            articleListenerMsg.setUserId(userId);
            articleListenerMsg.setCreated(created);
            articleListenerMsg.setArticleRid(articleRid);
            // 3 存储数据
            this.mongoTemplate.save(articleListenerMsg, "article_listener_msg");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
