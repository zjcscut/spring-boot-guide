package club.throwable.ch7.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/16 0:36
 */
@Data
public class Customer {

    private Long id;
    private String customerName;
    private LocalDateTime createTime;
    private LocalDateTime editTime;
}
