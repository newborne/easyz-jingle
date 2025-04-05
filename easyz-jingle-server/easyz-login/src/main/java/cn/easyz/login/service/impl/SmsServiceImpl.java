package cn.easyz.login.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.easyz.common.config.aliyun.AliyunSMSConfig;
import cn.easyz.common.model.enums.ResultCodeEnum;
import cn.easyz.common.model.vo.ResponseResult;
import cn.easyz.login.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Sms service.
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private AliyunSMSConfig aliyunSMSConfig;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ResponseResult sendCheckCode(String mobile) {
        String redisKey = "CHECK_CODE_" + mobile;
        // 1 判断验证码是否发送过
        if (Boolean.TRUE.equals(this.redisTemplate.hasKey(redisKey))) {
            return ResponseResult.build(mobile, ResultCodeEnum.SEND_CHECKCODE_ERROR_2);
        }
        // 2 发送验证码
        String code = "123456";
        // String code = this.sendSms(mobile);
        if (StringUtils.isEmpty(code)) {
            return ResponseResult.build(null, ResultCodeEnum.SEND_CHECKCODE_ERROR_1);
        }
        // 3 将验证码存入redis
        this.redisTemplate.opsForValue().set(redisKey, code, Duration.ofMinutes(5));
        return ResponseResult.ok();
    }
    // 云之讯
    private String sendSms(String mobile) {
        // 1 参数设定
        String url = "https://open.ucpaas.com/ol/sms/sendsms";
        Map<String, Object> params = new HashMap<>();
        params.put("sid", "1bafadef5706824e2715386ab3ed7a6d");
        params.put("token", "5c1128d340108f9826c0015e9e112893");
        params.put("appid", "b3b0dec8f97244f28293eaa7349f6b08");
        params.put("templateid", "487656");
        params.put("mobile", mobile);
        params.put("param", RandomUtils.nextInt(100000, 999999));
        // 2 发送请求
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, params, String.class);
        String body = responseEntity.getBody();
        // 3 解析返回结果
        JsonNode jsonNode = null;
        try {
            jsonNode = MAPPER.readTree(body);
        } catch (JsonProcessingException e) {
            System.out.println("类" + this.getClass().getName() + "中" + Thread.currentThread()
                    .getStackTrace()[1].getMethodName() + "方法：" + "发送短信到云之讯失败");
        }
        // 查询码 http://docs.ucpaas.com/doku.php?id=error_code
        return (StringUtils.equals(jsonNode.get("code").textValue(),
                "000000")) ? String.valueOf(params.get("param")) : null;
    }
//    // 阿里云SMS
//    private String sendSms(String mobile) {
//        DefaultProfile profile = DefaultProfile.getProfile(this.aliyunSMSConfig.getRegionId(),
//                this.aliyunSMSConfig.getAccessKeyId(), this.aliyunSMSConfig.getAccessKeySecret());
//        IAcsClient client = new DefaultAcsClient(profile);
//        String code = RandomUtils.nextInt(100000, 999999) + "";
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain(this.aliyunSMSConfig.getDomain());
//        request.setSysVersion("2017-05-25");
//        request.setSysAction("SendSms");
//        request.putQueryParameter("RegionId", this.aliyunSMSConfig.getRegionId());
//        request.putQueryParameter("PhoneNumbers", mobile); //目标手机号
//        request.putQueryParameter("SignName", this.aliyunSMSConfig.getSignName()); //签名名称
//        request.putQueryParameter("TemplateCode", this.aliyunSMSConfig.getTemplateCode()); //短信模板code
//        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");//模板中变量替换
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            String data = response.getData();
//            if (StringUtils.contains(data, "\"Message\":\"OK\"")) {
//                return code;
//            }
//            log.info("发送短信验证码失败~ data = " + data);
//        } catch (Exception e) {
//            log.error("发送短信验证码失败~ mobile = " + mobile, e);
//        }
//        return null;
//    }
}
