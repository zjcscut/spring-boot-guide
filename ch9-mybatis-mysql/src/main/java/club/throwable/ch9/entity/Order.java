package club.throwable.ch9.entity;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @author throwable
 * @since 2020/8/2 13:01
 */
@Data
public class Order {

    private Long id;
    private String orderId;
    private OffsetDateTime createTime;
    private OffsetDateTime editTime;
}
