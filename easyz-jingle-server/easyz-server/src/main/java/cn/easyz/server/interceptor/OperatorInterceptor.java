package cn.easyz.server.interceptor;

import cn.easyz.common.util.UserThreadLocal;
import cn.easyz.dubbo.api.v1.LogApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * The type Operator interceptor.
 */
@Component
public class OperatorInterceptor implements HandlerInterceptor {
    @DubboReference(version = "1.0.0")
    private LogApi logApi;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String annotation = Arrays.toString(((HandlerMethod) handler).getMethod().getDeclaredAnnotations());
        this.logApi.save(Long.valueOf(UserThreadLocal.get().getId()), request.getRequestURI(), annotation);
        return true;
    }
}
