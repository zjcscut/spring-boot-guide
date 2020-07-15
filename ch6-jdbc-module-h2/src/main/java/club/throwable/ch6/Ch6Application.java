package club.throwable.ch6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/15 22:25
 */
@SpringBootApplication
public class Ch6Application implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(Ch6Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(dataSource);
    }
}
