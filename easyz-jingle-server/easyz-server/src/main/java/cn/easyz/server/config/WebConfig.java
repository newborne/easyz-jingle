package cn.easyz.server.config;

import cn.easyz.server.interceptor.OperatorInterceptor;
import cn.easyz.server.interceptor.RedisCacheInterceptor;
import cn.easyz.server.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The type Web config.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private RedisCacheInterceptor redisCacheInterceptor;
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private OperatorInterceptor operatorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注意拦截器的顺序
        registry.addInterceptor(this.tokenInterceptor).addPathPatterns("/**");
        registry.addInterceptor(this.operatorInterceptor).addPathPatterns("/**");
        registry.addInterceptor(this.redisCacheInterceptor).addPathPatterns("/**");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("https://cn-sz-yd-plustmp2.natfrp.cloud")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
}
