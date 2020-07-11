package club.throwable.ch10086.service;

import club.throwable.ch10086.common.OrderStatus;
import club.throwable.ch10086.dao.OrderDao;
import club.throwable.ch10086.service.dto.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/11 15:33
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderDao orderDao;

    private static final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<OrderDTO> queryByScrollingPagination(String paymentDateTimeStart,
                                                     String paymentDateTimeEnd,
                                                     long lastBatchMaxId,
                                                     int limit) {
        LocalDateTime start = LocalDateTime.parse(paymentDateTimeStart, F);
        LocalDateTime end = LocalDateTime.parse(paymentDateTimeEnd, F);
        return orderDao.queryByScrollingPagination(lastBatchMaxId, limit, start, end).stream().map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setId(order.getId());
            dto.setAmount(order.getAmount());
            dto.setOrderId(order.getOrderId());
            dto.setPaymentTime(order.getPaymentTime().format(F));
            dto.setOrderStatus(OrderStatus.fromStatus(order.getOrderStatus()).getDescription());
            return dto;
        }).collect(Collectors.toList());
    }
}
