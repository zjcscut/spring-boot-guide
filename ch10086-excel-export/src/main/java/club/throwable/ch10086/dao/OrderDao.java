package club.throwable.ch10086.dao;

import club.throwable.ch10086.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/11 16:16
 */
@RequiredArgsConstructor
@Repository
public class OrderDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Order> queryByScrollingPagination(long lastBatchMaxId,
                                                  int limit,
                                                  LocalDateTime paymentDateTimeStart,
                                                  LocalDateTime paymentDateTimeEnd) {
        return jdbcTemplate.query("SELECT * FROM t_order WHERE id > ? AND payment_time >= ? AND payment_time <= ? " +
                        "ORDER BY id ASC LIMIT ?",
                p -> {
                    p.setLong(1, lastBatchMaxId);
                    p.setTimestamp(2, Timestamp.valueOf(paymentDateTimeStart));
                    p.setTimestamp(3, Timestamp.valueOf(paymentDateTimeEnd));
                    p.setInt(4, limit);
                },
                rs -> {
                    List<Order> orders = new ArrayList<>();
                    while (rs.next()) {
                        Order order = new Order();
                        order.setId(rs.getLong("id"));
                        order.setCreator(rs.getString("creator"));
                        order.setEditor(rs.getString("editor"));
                        order.setCreateTime(OffsetDateTime.ofInstant(rs.getTimestamp("create_time").toInstant(), ZoneId.systemDefault()));
                        order.setEditTime(OffsetDateTime.ofInstant(rs.getTimestamp("edit_time").toInstant(), ZoneId.systemDefault()));
                        order.setVersion(rs.getLong("version"));
                        order.setDeleted(rs.getInt("deleted"));
                        order.setOrderId(rs.getString("order_id"));
                        order.setAmount(rs.getBigDecimal("amount"));
                        order.setPaymentTime(OffsetDateTime.ofInstant(rs.getTimestamp("payment_time").toInstant(), ZoneId.systemDefault()));
                        order.setOrderStatus(rs.getInt("order_status"));
                        orders.add(order);
                    }
                    return orders;
                });
    }
}
