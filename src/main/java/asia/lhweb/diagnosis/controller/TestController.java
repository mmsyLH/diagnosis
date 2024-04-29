package asia.lhweb.diagnosis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :罗汉
 * @date : 2024/4/29
 */
@RestController
public class TestController {
    @RequestMapping("/hello")
    public String hello(){
        System.out.println("hello");
        return "index";
    }
}
