package cn.easyz.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * The type Id generator.
 */
@Service
public class IdGenerator {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * Create id long.
     *
     * @param type     the type
     * @param objectId the object id
     * @return the long
     */
    public Long createId(String type, String objectId) {
        type = StringUtils.upperCase(type);
        String hashKey = "ID_HASH_" + type;
        //如果存在objectid 话就返回相应的id
        Long id = (Long) this.redisTemplate.opsForHash().get(hashKey, objectId);
        if (id == null) {
            String key = "ID_INCREMENT_" + type;
            id = this.redisTemplate.opsForValue().increment(key);
            //将生成的id写入redis中
            this.redisTemplate.opsForHash().put(hashKey, objectId, id);
        }
        return id;
    }
}
