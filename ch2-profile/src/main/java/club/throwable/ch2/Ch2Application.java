package club.throwable.ch2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/5 11:30
 */
@Slf4j
@SpringBootApplication
public class Ch2Application implements CommandLineRunner {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(Ch2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Active profiles:{}", Arrays.toString(environment.getActiveProfiles()));
        log.info("app.author:{}", environment.getProperty("app.author"));
    }
}
