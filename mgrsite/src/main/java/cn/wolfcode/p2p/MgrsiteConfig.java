package cn.wolfcode.p2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 后台项目启动类
 * Created by WangZhe on 2018/7/20.
 */
@Import(CoreConfig.class)
@EnableScheduling
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MgrsiteConfig {
    public static void main(String[] args) {
        SpringApplication.run(MgrsiteConfig.class,args);
    }
}
