package cn.easyz.server.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.easyz.common.model.enums.MsgEnum;
import cn.easyz.common.model.pojo.mongodb.MaterialListenerMsg;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * The type Material listener.
 */
@Component
@RocketMQMessageListener(topic = "EASYZ_MATERIAL_TOPIC", consumerGroup = "MATERIAL_CONSUMER_GROUP")
public class MaterialListener implements RocketMQListener<String> {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void onMessage(String s) {
        try {
            // 1 读取消息
            JsonNode jsonNode = MAPPER.readTree(s);
            Long userId = jsonNode.get("userId").asLong();
            MsgEnum type = MsgEnum.values()[jsonNode.get("type").asInt()];
            Long created = jsonNode.get("created").asLong();
            Long materialRid = jsonNode.get("materialRid").asLong();
            // 2 填充数据
            MaterialListenerMsg materialListenerMsg = new MaterialListenerMsg();
            double rating = 0;
            // 2.1 填充rating
            switch (type) {
                case LOVE:
                    rating = 10;
                    break;
                case UNLOVE:
                    rating = -10;
                    break;
                case QUERY:
                    rating = 5;
                    break;
                default:
                    break;
            }
            materialListenerMsg.setRating(rating);
            // 2.2 填充其他字段
            materialListenerMsg.setUserId(userId);
            materialListenerMsg.setCreated(created);
            materialListenerMsg.setMaterialRid(materialRid);
            // 3 存储数据
            this.mongoTemplate.save(materialListenerMsg, "material_listener_msg");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
