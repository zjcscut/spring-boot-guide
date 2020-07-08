package club.throwable.ch3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/8 23:39
 */
@SpringBootApplication
public class Ch3Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Ch3Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Ch3Application.class);
    }
}
