package cn.easyz.neo4j;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * The type Neo 4 j application.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableNeo4jRepositories("cn.easyz.common.model.repository")
@MapperScan("cn.easyz.common.model.mapper")
@EntityScan("cn.easyz.common.model.pojo.neo4j")
@ComponentScan("cn.easyz")
@EnableFeignClients("cn.easyz.common.client")
public class Neo4jApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Neo4jApplication.class, args);
    }
}
