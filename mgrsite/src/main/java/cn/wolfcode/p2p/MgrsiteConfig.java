package cn.wolfcode.p2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 后台项目启动类
 * Created by WangZhe on 2018/7/20.
 */
@SpringBootApplication
@Import(CoreConfig.class)
@EnableScheduling
public class MgrsiteConfig {
    public static void main(String[] args) {
        SpringApplication.run(MgrsiteConfig.class,args);
    }
}
