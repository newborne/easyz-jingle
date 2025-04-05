package cn.easyz.server.interceptor;

import cn.easyz.common.client.LoginFeignClient;
import cn.easyz.common.model.pojo.mysql.ApUser;
import cn.easyz.common.util.UserThreadLocal;
import cn.easyz.server.annotation.NoAuthorization;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Token interceptor.
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginFeignClient loginFeignClient;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            NoAuthorization noAnnotation = handlerMethod.getMethod().getAnnotation(NoAuthorization.class);
            if (noAnnotation != null) {
                //如果该方法被标记为无需验证token,直接返回即可
                return true;
            }
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token)) {
            ApUser user = this.loginFeignClient.queryUserByToken(token);
            if (null != user) {
                //将当前对象存储到线程中去
                UserThreadLocal.set(user);
                return true;
            }
        }
        //请求头中如不存在Authorization直接返回false
        response.setStatus(401); //无权限访问
        return false;
    }
}
