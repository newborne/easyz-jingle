package cn.easyz.server.annotation;

import java.lang.annotation.*;

/**
 * The interface Cache.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented //标记注解
public @interface Cache {
    /**
     * Time string.
     *
     * @return the string
     */
    String time() default "60";
}
