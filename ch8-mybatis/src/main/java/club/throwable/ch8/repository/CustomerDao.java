package club.throwable.ch8.repository;

import club.throwable.ch8.entity.Customer;
import club.throwable.ch8.repository.mapper.CustomerMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/19 11:30
 */
public interface CustomerDao extends CustomerMapper {

    Customer queryByName(@Param("customerName") String customerName);
}
