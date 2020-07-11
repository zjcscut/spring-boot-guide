package club.throwable.ch10086.controller;

import club.throwable.ch10086.service.OrderService;
import club.throwable.ch10086.service.dto.OrderDTO;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/11 16:45
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path = "/export")
    public void export(@RequestParam(name = "paymentDateTimeStart") String paymentDateTimeStart,
                       @RequestParam(name = "paymentDateTimeEnd") String paymentDateTimeEnd,
                       HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();
        String fileName = URLEncoder.encode(String.format("%s-(%s).xlsx", "订单支付数据", UUID.randomUUID().toString()),
                StandardCharsets.UTF_8.toString());
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ExcelWriter writer = new ExcelWriterBuilder()
                .autoCloseStream(true)
                .excelType(ExcelTypeEnum.XLSX)
                .file(response.getOutputStream())
                .head(OrderDTO.class)
                .build();
        // xlsx文件上上限是104W行左右,这里如果超过104W需要分Sheet
        WriteSheet writeSheet = new WriteSheet();
        writeSheet.setSheetName("target");
        long lastBatchMaxId = 0L;
        int limit = 500;
        for (; ; ) {
            List<OrderDTO> list = orderService.queryByScrollingPagination(paymentDateTimeStart, paymentDateTimeEnd, lastBatchMaxId, limit);
            if (list.isEmpty()) {
                writer.finish();
                break;
            } else {
                lastBatchMaxId = list.stream().map(OrderDTO::getId).max(Long::compareTo).orElse(Long.MAX_VALUE);
                writer.write(list, writeSheet);
            }
        }
        log.info("导出数据耗时:{} ms,start:{},end:{}", System.currentTimeMillis() - start, paymentDateTimeStart, paymentDateTimeEnd);
    }
}
