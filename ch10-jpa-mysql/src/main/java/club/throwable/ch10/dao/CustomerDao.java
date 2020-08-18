package club.throwable.ch10.dao;

import club.throwable.ch10.entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author throwable
 * @since 2020/8/12 0:14
 */
public interface CustomerDao extends JpaRepository<Customer, Long> {

    List<Customer> queryByAgeBetween(Integer min, Integer max);

    /**
     * 分页
     */
    List<Customer> queryByFirstName(String firstName, Pageable pageable);

    /**
     * limit
     */
    List<Customer> queryTop5ByFirstName(String firstName);

    /**
     * 统计
     */
    long countByFirstName(String firstName);

    /**
     * 流式查询
     */
    Streamable<Customer> findByFirstNameContaining(String firstName);

    Streamable<Customer> findByLastNameContaining(String lastName);

    /**
     * 异步查询
     */
    @Async
    Future<Customer> findTop1ByFirstName(String firstName);

    @Async
    CompletableFuture<List<Customer>> findOneByFirstName(String firstName, Pageable pageable);

    @Async
    ListenableFuture<List<Customer>> findOneByLastName(String lastName, Pageable pageable);
}
