package club.throwable.ch5;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/12 20:34
 */
@Slf4j
@Configuration
public class CommandLineRunners {

    @Bean
    @Order(value = 2)
    public CommandLineRunner commandLineRunner(){
        return args -> log.info("CommandLineRunner runs...");
    }
}
