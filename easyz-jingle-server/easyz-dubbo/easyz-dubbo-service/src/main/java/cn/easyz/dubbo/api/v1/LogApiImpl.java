package cn.easyz.dubbo.api.v1;

import cn.easyz.common.model.pojo.mongodb.Log;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * The type Log api.
 */
@DubboService(version = "1.0.0")
public class LogApiImpl implements LogApi {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void save(Long userId, String requestURI, String annotation) {
        Log log = new Log(userId, System.currentTimeMillis(), requestURI, annotation);
        this.mongoTemplate.save(log);
    }
}
