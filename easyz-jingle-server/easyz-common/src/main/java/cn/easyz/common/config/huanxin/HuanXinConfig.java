package cn.easyz.common.config.huanxin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Huan xin config.
 */
@Configuration
@PropertySource("classpath:huanxin.properties")
@ConfigurationProperties(prefix = "huanxin")
@Data
public class HuanXinConfig {
    private String url;
    private String orgName;
    private String appName;
    private String clientId;
    private String clientSecret;
}