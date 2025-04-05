package cn.easyz.common.config.mybatis;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Mybatis plus config.
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 乐观锁插件
     *
     * @return the optimistic locker interceptor
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
    /**
     * 分页插件
     *
     * @return the pagination interceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    /**
     * Global config global config.
     *
     * @return the global config
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(new GeneralMetaObjectHandler());
        return globalConfig;
    }
}
