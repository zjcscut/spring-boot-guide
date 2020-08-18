package club.throwable.ch10.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author throwable
 * @since 2020/8/13 0:50
 */
@Configuration
@EntityScan(basePackages = "club.throwable.ch10.entity")
@EnableJpaRepositories(basePackages = "club.throwable.ch10.dao")
@EnableJpaAuditing
public class JpaAutoConfiguration {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setThreadNamePrefix("TaskExecutorWorker-");
        taskExecutor.setQueueCapacity(100);
        return taskExecutor;
    }
}
