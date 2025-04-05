package cn.easyz.server.annotation;

import java.lang.annotation.*;

/**
 * The interface No authorization.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented //标记注解
public @interface NoAuthorization {
}
