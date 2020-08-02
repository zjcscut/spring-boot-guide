package club.throwable;

import club.throwable.ch8.entity.Customer;
import club.throwable.ch8.repository.CustomerDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/19 11:26
 */
@Slf4j
@SpringBootApplication
public class Ch8Application implements CommandLineRunner {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(Ch8Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Customer customer = customerDao.queryByName("doge");
        log.info("Query [name=doge],result:{}", objectMapper.writeValueAsString(customer));
        customer = customerDao.queryByName("throwable");
        log.info("Query [name=throwable],result:{}", objectMapper.writeValueAsString(customer));
        Customer newCustomer = new Customer();
        newCustomer.setAge(11);
        newCustomer.setCustomerName("little throwable");
        int i = customerDao.insertSelective(newCustomer);
        log.info("Insert [name=little throwable],result:{}", objectMapper.writeValueAsString(newCustomer));
        List<Customer> customers = customerDao.selectByCreateTime(OffsetDateTime.now(), OffsetDateTime.now());
    }
}
