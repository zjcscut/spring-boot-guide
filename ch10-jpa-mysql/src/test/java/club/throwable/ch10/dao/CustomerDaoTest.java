package club.throwable.ch10.dao;

import club.throwable.ch10.Ch10Application;
import club.throwable.ch10.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author throwable
 * @since 2020/8/18 8:28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ch10Application.class)
public class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private ObjectMapper objectMapper;

    private String toJsonString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception ignore) {

        }
        return null;
    }

    @Before
    public void setUp() throws Exception {
        for (int i = 1; i < 10; i++) {
            Customer newCustomer = new Customer();
            newCustomer.setFirstName("张");
            newCustomer.setLastName(i + "狗");
            newCustomer.setAge(10 + i);
            Customer result = customerDao.saveAndFlush(newCustomer);
            log.info("保存{}成功...", result);
        }
    }

    @After
    public void tearDown() throws Exception {
        customerDao.deleteAll();
    }

    @Test
    public void queryByAgeBetween() {
        List<Customer> customers = customerDao.queryByAgeBetween(11, 13);
        log.info("queryByAgeBetween,result:{}", toJsonString(customers));
        Assert.assertEquals(11, (long) customers.get(0).getAge());
        Assert.assertEquals(13, (long) customers.get(customers.size() - 1).getAge());
    }

    @Test
    public void queryByFirstName() {
        // 这里好神奇,分页的page从0开始,不是认知中的1
        List<Customer> customers = customerDao.queryByFirstName("张", PageRequest.of(1, 5));
        log.info("queryByFirstName,result:{}", toJsonString(customers));
        Assert.assertEquals(4, customers.size());
        for (Customer customer : customers) {
            Assert.assertEquals("张", customer.getFirstName());
        }
    }

    @Test
    public void queryTop5ByFirstName() {
        List<Customer> customers = customerDao.queryTop5ByFirstName("张");
        log.info("queryTop5ByFirstName,result:{}", toJsonString(customers));
        Assert.assertEquals(5, customers.size());
        for (Customer customer : customers) {
            Assert.assertEquals("张", customer.getFirstName());
        }
    }

    @Test
    public void countByFirstName() {
        Assert.assertEquals(9L, customerDao.countByFirstName("张"));
    }

    @Test
    public void findByFirstNameContaining() {
        Streamable<Customer> stream = customerDao.findByFirstNameContaining("张");
        List<Customer> customers = stream.stream().collect(Collectors.toList());
        Assert.assertEquals(9, customers.size());
        for (Customer customer : customers) {
            Assert.assertEquals("张", customer.getFirstName());
        }
    }

    @Test
    public void findByLastNameContaining() {
        Streamable<Customer> stream = customerDao.findByLastNameContaining("狗");
        List<Customer> customers = stream.stream().collect(Collectors.toList());
        Assert.assertEquals(9, customers.size());
        for (Customer customer : customers) {
            Assert.assertEquals("张", customer.getFirstName());
        }
    }

    @Test
    public void findTop1ByFirstName() throws Exception {
        Future<Customer> future = customerDao.findTop1ByFirstName("张");
        Assert.assertNotNull(future.get());
        Assert.assertEquals(future.get().getFirstName(), "张");
    }

    @Test
    public void findOneByFirstName() throws Exception {
        CompletableFuture<List<Customer>> future = customerDao.findOneByFirstName("张", PageRequest.of(0, 1));
        List<Customer> customers = future.get();
        Assert.assertNotNull(customers);
        Assert.assertEquals(1, customers.size());
        Assert.assertEquals(customers.get(0).getFirstName(), "张");
    }

    @Test
    public void findOneByLastName() throws Exception {
        ListenableFuture<List<Customer>> future = customerDao.findOneByLastName("1狗", PageRequest.of(0, 1));
        List<Customer> customers = future.get();
        Assert.assertNotNull(customers);
        Assert.assertEquals(1, customers.size());
        Assert.assertEquals(customers.get(0).getLastName(), "1狗");
    }
}