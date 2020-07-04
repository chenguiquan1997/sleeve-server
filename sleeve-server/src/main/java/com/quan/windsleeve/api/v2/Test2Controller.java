package com.quan.windsleeve.api.v2;

import com.quan.windsleeve.OCPTest.reflect.Kill;
import com.quan.windsleeve.exception.http.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/banner")
public class Test2Controller {

    @Autowired
    private Kill kill;

//    构造方法注入，官方也推荐使用构造方法注入
//    @Autowired
//    public TestController(Gailun gai) {
//        this.gailun = gai;
//    }

//    Setter方法注入
//    @Autowired
//    public void setGailun(Gailun gailun) {
//        this.gailun = gailun;
//    }

    @GetMapping("/test")
    public String test() throws Exception {
        kill.r();
        throw new NotFoundException(10000);
        //return "hello , 风袖ssssss！";
    }
}
