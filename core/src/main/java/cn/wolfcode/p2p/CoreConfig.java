package cn.wolfcode.p2p;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by WangZhe on 2018/7/20.
 */
@Configuration
@MapperScan("cn.wolfcode.p2p.*.mapper")
@PropertySource("application-core.properties")
@SpringBootApplication
public class CoreConfig {
//    public static void main(String[] args) {
//        SpringApplication.run(CoreConfig.class, args);
//    }
}
