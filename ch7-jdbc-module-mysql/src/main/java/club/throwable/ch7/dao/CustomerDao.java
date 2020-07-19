package club.throwable.ch7.dao;

import club.throwable.ch7.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/16 0:36
 */
@RequiredArgsConstructor
@Repository
public class CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    /**
     * 增
     */
    public int insertSelective(Customer customer) {
        StringJoiner p = new StringJoiner(",", "(", ")");
        StringJoiner v = new StringJoiner(",", "(", ")");
        Optional.ofNullable(customer.getCustomerName()).ifPresent(x -> {
            p.add("customer_name");
            v.add("?");
        });
        Optional.ofNullable(customer.getCreateTime()).ifPresent(x -> {
            p.add("create_time");
            v.add("?");
        });
        Optional.ofNullable(customer.getEditTime()).ifPresent(x -> {
            p.add("edit_time");
            v.add("?");
        });
        String sql = "INSERT INTO customer" + p.toString() + " VALUES " + v.toString();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updateCount = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            if (null != customer.getCustomerName()) {
                ps.setString(index++, customer.getCustomerName());
            }
            if (null != customer.getCreateTime()) {
                ps.setTimestamp(index++, Timestamp.valueOf(customer.getCreateTime()));
            }
            if (null != customer.getEditTime()) {
                ps.setTimestamp(index, Timestamp.valueOf(customer.getEditTime()));
            }
            return ps;
        }, keyHolder);
        customer.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return updateCount;
    }

    /**
     * 删
     */
    public int delete(long id) {
        return jdbcTemplate.update("DELETE FROM customer WHERE id = ?", id);
    }

    /**
     * 查
     */
    public Customer queryByCustomerName(String customerName) {
        return jdbcTemplate.query("SELECT * FROM customer WHERE customer_name = ?",
                ps -> ps.setString(1, customerName), SINGLE);
    }

    public List<Customer> queryAll() {
        return jdbcTemplate.query("SELECT * FROM customer", MULTI);
    }

    public int updateByPrimaryKeySelective(Customer customer) {
        final long id = Objects.requireNonNull(Objects.requireNonNull(customer).getId());
        StringBuilder sql = new StringBuilder("UPDATE customer SET ");
        Optional.ofNullable(customer.getCustomerName()).ifPresent(x -> sql.append("customer_name = ?,"));
        Optional.ofNullable(customer.getCreateTime()).ifPresent(x -> sql.append("create_time = ?,"));
        Optional.ofNullable(customer.getEditTime()).ifPresent(x -> sql.append("edit_time = ?,"));
        StringBuilder q = new StringBuilder(sql.substring(0, sql.lastIndexOf(","))).append(" WHERE id = ?");
        return jdbcTemplate.update(q.toString(), ps -> {
            int index = 1;
            if (null != customer.getCustomerName()) {
                ps.setString(index++, customer.getCustomerName());
            }
            if (null != customer.getCreateTime()) {
                ps.setTimestamp(index++, Timestamp.valueOf(customer.getCreateTime()));
            }
            if (null != customer.getEditTime()) {
                ps.setTimestamp(index++, Timestamp.valueOf(customer.getEditTime()));
            }
            ps.setLong(index, id);
        });
    }

    private static Customer convert(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong("id"));
        customer.setCustomerName(rs.getString("customer_name"));
        customer.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
        customer.setEditTime(rs.getTimestamp("edit_time").toLocalDateTime());
        return customer;
    }

    private static final ResultSetExtractor<List<Customer>> MULTI = rs -> {
        List<Customer> result = new ArrayList<>();
        while (rs.next()) {
            result.add(convert(rs));
        }
        return result;
    };

    private static final ResultSetExtractor<Customer> SINGLE = rs -> rs.next() ? convert(rs) : null;
}
