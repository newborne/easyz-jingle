package cn.easyz.common.config.arcsoft;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Arcsoft config.
 */
@Configuration
@PropertySource("classpath:arcsoft.properties")
@ConfigurationProperties(prefix = "arcsoft")
@Data
public class ArcsoftConfig {
    private String appid;
    private String sdkKey;
    private String libPath;
}
