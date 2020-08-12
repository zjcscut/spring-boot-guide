package club.throwable.ch10.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author throwable
 * @since 2020/8/13 0:50
 */
@Configuration
@EnableJpaRepositories(basePackages = "club.throwable.ch10.dao")
public class JpaAutoConfiguration {

}
