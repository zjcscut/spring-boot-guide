package club.throwable.ch9;

import club.throwable.ch9.entity.Order;
import club.throwable.ch9.mapper.OrderMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/19 11:26
 */
@Slf4j
@SpringBootApplication
public class Ch9Application implements CommandLineRunner {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(Ch9Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        int insert = orderMapper.insert(order);
        log.info("写入{}条订单数据,id:{}", insert, order.getId());
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        OffsetDateTime end = OffsetDateTime.now(zoneId);
        OffsetDateTime start = end.plusMonths(-1L);
        List<Order> orders = orderMapper.selectByDuration(start, end);
        log.info(objectMapper.writeValueAsString(orders));
    }
}
