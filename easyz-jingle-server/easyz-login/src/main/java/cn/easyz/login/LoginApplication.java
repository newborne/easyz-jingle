package cn.easyz.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * The type Login application.
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.easyz.common.model.mapper")
@ComponentScan("cn.easyz")
@EnableFeignClients("cn.easyz.common.client")
public class LoginApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }
}
