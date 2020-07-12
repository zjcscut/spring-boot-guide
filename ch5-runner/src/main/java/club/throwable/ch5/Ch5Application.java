package club.throwable.ch5;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/12 21:47
 */
@Slf4j
@SpringBootApplication
public class Ch5Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Ch5Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Ch5Application CommandLineRunner runs...");
    }
}
