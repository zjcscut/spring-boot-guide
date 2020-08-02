package club.throwable.ch9.mapper;

import club.throwable.ch9.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author throwable
 * @since 2020/8/2 11:56
 */
public interface OrderMapper {

    int insert(@Param("record") Order record);

    List<Order> selectByDuration(@Param("start") OffsetDateTime start,
                                 @Param("end") OffsetDateTime end);
}
