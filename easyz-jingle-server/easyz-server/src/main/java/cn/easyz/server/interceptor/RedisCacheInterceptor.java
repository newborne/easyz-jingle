package cn.easyz.server.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import cn.easyz.server.annotation.Cache;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Redis cache interceptor.
 */
@Component
public class RedisCacheInterceptor implements HandlerInterceptor {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Value("${easyz.cache.enable}")
    private boolean enable;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * Create redis key string.
     *
     * @param request the request
     * @return the string
     * @throws Exception the exception
     */
    public static String createRedisKey(HttpServletRequest request) throws Exception {
        String url = request.getRequestURI();
        String param = MAPPER.writeValueAsString(request.getParameterMap());
        String token = request.getHeader("Authorization");
        String data = url + "_" + param + "_" + token;
        return "SERVER_CACHE_DATA_" + DigestUtils.md5Hex(data);
    }
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //判断缓存全局的开关是否打开
        if (!enable) {
            return true;
        }
        //判断handler是否是HandlerMethod
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //判读是否是get请求
        if (!((HandlerMethod) handler).hasMethodAnnotation(GetMapping.class)) {
            return true;
        }
        //判断是否添加了cache注解
        if (!((HandlerMethod) handler).hasMethodAnnotation(Cache.class)) {
            return true;
        }
        //缓存命中
        String redisKey = createRedisKey(request);
        String cacheData = this.redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isEmpty(cacheData)) {
            return true;
        }
        //将data数据进行相应
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(cacheData);
        return false;
    }
}

