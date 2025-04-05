package cn.easyz.common.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Jwt config.
 */
@Configuration
@PropertySource("classpath:jwt.properties")
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
    private String secret;
}
