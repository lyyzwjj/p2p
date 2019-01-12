package cn.wolfcode.p2p;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Created by WangZhe on 2018/7/20.
 */
@SpringBootApplication
@Import(CoreConfig.class)
public class WebsiteConfig {
    public static void main(String[] args) {
        SpringApplication.run(WebsiteConfig.class, args);
    }
}
