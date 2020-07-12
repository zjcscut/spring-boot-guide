package club.throwable.ch5;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/12 20:31
 */
@Slf4j
@Component
@Order(value = 1)
@Profile(value = "dev")
public class CustomCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("CustomCommandLineRunner runs...");
    }
}
