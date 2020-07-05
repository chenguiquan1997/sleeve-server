package com.quan.windsleeve.api.v1;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.quan.windsleeve.OCPTest.reflect.Kill;
import com.quan.windsleeve.core.dto.PersonDTO;
import com.quan.windsleeve.exception.http.NotFoundException;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@Validated
public class TestController {

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
    @PostMapping("/hello/{id}")
    @ResponseBody
    public String hello(@PathVariable  @Range(min = 1,max = 10) Integer id,
                        @RequestParam String name,
                        // @RequestBody Map<String,Object> person
                        @RequestBody @Validated PersonDTO personDTO) {
        //PersonDTO personDTO1 = new PersonDTO();
//        PersonDTO p = new PersonDTO();
//        p.setName(null);
//        p.setAge(1000);
        return "hello world";
    }

    @GetMapping("/test")
    public String test() throws Exception {
        kill.r();
        throw new NotFoundException(10000);
        //return "hello , 风袖ssssss！";
    }
    @GetMapping("/getLocation")
    public JSONObject getLocation() {
        String appkey = "0d3d3847acc24837b2752698266070dc";
        HashMap<String,Object> map = new HashMap<>();
        map.put("key",appkey);
        map.put("address","杭州市西湖风景区");
        String url = "https://restapi.amap.com/v3/geocode/geo";
        String result = HttpUtil.get(url,map);
        System.out.println(result);
        JSONObject json = JSON.parseObject(result);
        JSONArray jsonArray = json.getJSONArray("geocodes");
        JSONObject item = (JSONObject) jsonArray.get(0);
        String location = item.getString("location");
        System.out.println(location);
        return json;
    }
}
