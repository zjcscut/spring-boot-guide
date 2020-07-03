package club.throwable.ch1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author throwable
 * @version v1.0
 * @description
 * @since 2020/7/3 8:31
 */
@Slf4j
@RestController
@RequestMapping(path = "/ch1")
public class HelloController {

    @GetMapping(path = "/hello")
    public ResponseEntity<String> hello(@RequestParam(name = "name") String name) {
        String value = String.format("[%s] say hello", name);
        log.info("调用[/hello]接口,参数:{},响应结果:{}", name, value);
        return ResponseEntity.of(Optional.of(value));
    }
}
