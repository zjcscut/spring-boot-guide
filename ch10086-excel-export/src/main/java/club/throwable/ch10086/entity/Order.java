package club.throwable.ch10086.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/11 16:18
 */
@Data
public class Order {

    private Long id;
    private String creator;
    private String editor;

    private OffsetDateTime createTime;
    private OffsetDateTime editTime;
    private Long version;
    private Integer deleted;

    private String orderId;
    private BigDecimal amount;
    private OffsetDateTime paymentTime;

    private Integer orderStatus;
}
