package club.throwable.ch10086.service;

import com.mysql.cj.jdbc.Driver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/11 15:33
 */
@Slf4j
public class OrderServiceTest {

    private static final Random OR = new Random();
    private static final Random AR = new Random();
    private static final Random DR = new Random();

    @Test
    public void testGenerateTestOrderSql() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setPassword("root");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/local?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false");
        config.setDriverClassName(Driver.class.getName());
        HikariDataSource hikariDataSource = new HikariDataSource(config);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(hikariDataSource);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<?>> futures = new ArrayList<>();
        for (int d = 0; d < 100; d++) {
            futures.add(executor.submit(() -> {
                String item = "('%s','%d','2020-07-%d 00:00:00','%d')";
                StringBuilder sql = new StringBuilder("INSERT INTO t_order(order_id,amount,payment_time,order_status) VALUES ");
                for (int i = 0; i < 20_000; i++) {
                    sql.append(String.format(item, UUID.randomUUID().toString().replace("-", ""),
                            AR.nextInt(100000) + 1, DR.nextInt(31) + 1, OR.nextInt(3))).append(",");
                }
                jdbcTemplate.update(sql.substring(0, sql.lastIndexOf(",")));
            }));
        }
        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
        hikariDataSource.close();
    }
}