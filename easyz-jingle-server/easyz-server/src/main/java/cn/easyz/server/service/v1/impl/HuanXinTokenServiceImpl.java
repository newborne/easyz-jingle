package cn.easyz.server.service.v1.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.easyz.common.config.huanxin.HuanXinConfig;
import cn.easyz.server.service.v1.HuanXinTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The type Huan xin token service.
 */
@Service
public class HuanXinTokenServiceImpl implements HuanXinTokenService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String tokenRedisKey = "HUANXIN_TOEKN";
    @Autowired
    private HuanXinConfig huanXinConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public String getToken() {
        //先从Redis中命中
        String cacheData = this.redisTemplate.opsForValue().get(tokenRedisKey);
        if (StringUtils.isNotEmpty(cacheData)) {
            return cacheData;
        }
        String url = this.huanXinConfig.getUrl()
                + this.huanXinConfig.getOrgName() + "/"
                + this.huanXinConfig.getAppName() + "/token";
        Map<String, Object> param = new HashMap<>();
        param.put("grant_type", "client_credentials");
        param.put("client_id", this.huanXinConfig.getClientId());
        param.put("client_secret", this.huanXinConfig.getClientSecret());
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, param, String.class);
        if (responseEntity.getStatusCodeValue() != 200) {
            return null;
        }
        String body = responseEntity.getBody();
        try {
            JsonNode jsonNode = MAPPER.readTree(body);
            String accessToken = jsonNode.get("access_token").asText();
            // 过期时间，提前一天失效
            Long expiresIn = jsonNode.get("expires_in").asLong() - 86400;
            //将token值存储到本地，存储到Redis中
            this.redisTemplate.opsForValue().set(tokenRedisKey, accessToken, expiresIn, TimeUnit.SECONDS);
            return accessToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
