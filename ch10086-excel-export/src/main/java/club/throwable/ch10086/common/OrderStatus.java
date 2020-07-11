package club.throwable.ch10086.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/11 16:24
 */
@RequiredArgsConstructor
@Getter
public enum OrderStatus {

    //
    PROCESSING(0, "处理中"),

    SUCCESS(1, "支付成功"),

    FAIL(2, "支付失败"),

    ;

    private final Integer status;
    private final String description;

    public static OrderStatus fromStatus(Integer status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getStatus().equals(status)) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("status = " + status);
    }
}
