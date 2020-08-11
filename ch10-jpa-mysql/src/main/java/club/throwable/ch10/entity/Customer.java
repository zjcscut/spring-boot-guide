package club.throwable.ch10.entity;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * @author throwable
 * @since 2020/8/12 0:13
 */
@Data
public class Customer {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer age;

    private OffsetDateTime createTime;

    private OffsetDateTime editTime;
}
