package cn.easyz.login.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import cn.easyz.common.client.ServerFeignClient;
import cn.easyz.common.model.mapper.ApUserMapper;
import cn.easyz.common.model.pojo.mysql.ApUser;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.login.service.LoginService;
import cn.easyz.login.service.SmsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Login service.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private ApUserMapper apUserMapper;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.secret}")
    private String secret;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private ServerFeignClient serverFeignClient;
    @Autowired
    private SmsService smsService;
    @Override
    public ResponseResult login(String mobile, String code) {
        // 1 redis中获取验证码并校验
        String redisKey = "CHECK_CODE_" + mobile;
        String redisData = this.redisTemplate.opsForValue().get(redisKey);
        if (!StringUtils.equals(code, redisData) || StringUtils.isEmpty(redisData)) {
            return null;
        }
        this.redisTemplate.delete(redisKey);
        // 2 判断是否是新用户
        boolean isNew = false;
        ApUser user = new LambdaQueryChainWrapper<>(apUserMapper).eq(ApUser::getMobile, mobile).one();
        if (user == null) {
            user = new ApUser();
            user.setMobile(mobile);
            user.setPassword(DigestUtils.md5Hex("123456"));
            this.apUserMapper.insert(user);
            isNew = true;
        }
        // 3 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofDays(1000).toMillis()))
                .compact();
        // 4 环信注册
        if (isNew) {
            this.serverFeignClient.register(Long.valueOf(user.getId()), token);
        }
        // 5 发送登录成功消息
        Map<String, Object> map = new HashMap<>();
        map.put("isNew", isNew);
        map.put("token", token);
        try {
            Map<String, Object> msg = new HashMap<>();
            msg.put("id", user.getId());
            msg.put("created", System.currentTimeMillis());
            this.rocketMQTemplate.convertAndSend("EASYZ_LOGIN_TOPIC", msg);
            System.out.println("类" + this.getClass().getName() + "中" + Thread.currentThread()
                    .getStackTrace()[1].getMethodName() + "方法：" + "发送登录成功消息成功");
        } catch (Exception e) {
            System.out.println("类" + this.getClass().getName() + "中" + Thread.currentThread()
                    .getStackTrace()[1].getMethodName() + "方法：" + "发送登录成功消息失败");
        }
        return ResponseResult.ok(map);
    }
    @Override
    public ApUser queryUserByToken(String token) {
        try {
            // 1 解析token
            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            // 2 填充用户信息
            ApUser user = new ApUser();
            String id = body.get("id").toString();
            String exp = body.get("exp").toString();
            String redisKey = "USER_MOBILE_" + id;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
                user.setId(Integer.valueOf(id));
                user.setMobile(this.redisTemplate.opsForValue().get(redisKey));
            } else {
                user = this.apUserMapper.selectById(id);
                // 秒转毫秒
                long millis = Long.parseLong(exp) * 1000 - System.currentTimeMillis();
                this.redisTemplate.opsForValue()
                        .set(redisKey, user.getMobile(), Duration.ofMillis(millis));
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public ResponseResult saveUserInfo(Map<String, String> param, String token) {
        return this.serverFeignClient.saveUserInfo(param, token);
    }
    @Override
    public ResponseResult saveUserLogo(MultipartFile file, String token) {
        return this.serverFeignClient.saveUserLogo(file, token);
    }
    @Override
    public ResponseResult sendToOldMobile(String token) {
        String mobile = this.queryUserByToken(token).getMobile();
        return this.smsService.sendCheckCode(mobile);
    }
    @Override
    public ResponseResult updateNewMobile(String token, String mobile) {
        // 1 判断用户是否存在,手机号是否已经被注册
        if (null == this.queryUserByToken(token) || null != new LambdaQueryChainWrapper<>(apUserMapper).eq(ApUser::getMobile,
                mobile).one()) {
            return ResponseResult.fail();
        }
        // 2 更新用户信息
        ApUser user = new ApUser();
        user.setMobile(mobile);
        return (this.apUserMapper.updateById(user) > 0) ? ResponseResult.ok() : ResponseResult.fail();
    }
}
