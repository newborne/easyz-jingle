package cn.easyz.dubbo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * The type Dubbo application.
 */
@MapperScan("cn.easyz.common.model.mapper")
@ComponentScan("cn.easyz")
@SpringBootApplication
@EnableFeignClients("cn.easyz.common.client")
public class DubboApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DubboApplication.class, args);
    }
}

