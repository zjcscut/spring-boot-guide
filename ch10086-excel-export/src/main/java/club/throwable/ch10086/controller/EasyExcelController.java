package club.throwable.ch10086.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author throwable
 * @version v1
 * @description
 * @since 2020/9/9 23:58
 */
@Slf4j
@RestController
@RequestMapping(path = "/excel")
public class EasyExcelController {

    @PostMapping(path = "/upload")
    public ResponseEntity<?> upload(MultipartHttpServletRequest request) throws Exception {
        Map<String, MultipartFile> fileMap = request.getFileMap();
        for (Map.Entry<String, MultipartFile> part : fileMap.entrySet()) {
            InputStream inputStream = part.getValue().getInputStream();
            Map<Integer, String> head = new HashMap<>();
            List<Map<Integer, String>> data = new LinkedList<>();
            EasyExcel.read(inputStream).sheet()
                    .registerReadListener(new AnalysisEventListener<Map<Integer, String>>() {

                        @Override
                        public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                            head.putAll(headMap);
                        }

                        @Override
                        public void invoke(Map<Integer, String> row, AnalysisContext analysisContext) {
                            data.add(row);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                            log.info("读取文件[{}]成功,一共:{}行......", part.getKey(), data.size());
                        }
                    }).doRead();
            // 其他业务逻辑
        }
        return ResponseEntity.ok("success");
    }


    @GetMapping(path = "/download")
    public void download(HttpServletResponse response) throws Exception {
        // 这里文件名如果涉及中文一定要使用URL编码,否则会乱码
        String fileName = URLEncoder.encode("文件名.xlsx", StandardCharsets.UTF_8.toString());
        // 封装标题行
        List<List<String>> head = new ArrayList<>();
        // 封装数据
        List<List<String>> data = new LinkedList<>();
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        EasyExcel.write(response.getOutputStream())
                .head(head)
                .autoCloseStream(true)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("Sheet名字")
                .doWrite(data);
    }
}
