package club.throwable.ch7;

import club.throwable.ch7.dao.CustomerDao;
import club.throwable.ch7.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/15 22:25
 */
@Slf4j
@SpringBootApplication
public class Ch7Application implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(Ch7Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Connection connection = dataSource.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM customer WHERE id = 1");
        while (resultSet.next()) {
            log.info("id:{},name:{}", resultSet.getLong("id"), resultSet.getString("customer_name"));
        }
        resultSet.close();
        connection.close();
        Customer customer = new Customer();
        customer.setCustomerName("doge");
        int r = customerDao.insertSelective(customer);
        log.info("insert customer[doge],result:{},id:{}", r, customer.getId());
        customer.setCustomerName("throwableDoge");
        r = customerDao.updateByPrimaryKeySelective(customer);
        log.info("update customer[doge->throwableDoge],result:{}", r);
        Customer result = customerDao.queryByCustomerName("throwable");
        log.info("select customer[customerName=throwable],result:{}", objectMapper.writeValueAsString(result));
        List<Customer> all = customerDao.queryAll();
        log.info("select all customer,result:{}", objectMapper.writeValueAsString(all));
        r = customerDao.delete(customer.getId());
        log.info("delete customer[id={}],result:{}", customer.getId(), r);
    }
}
