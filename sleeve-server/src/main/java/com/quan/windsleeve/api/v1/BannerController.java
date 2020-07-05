package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.model.Banner;
import com.quan.windsleeve.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    @GetMapping("/name/{id}")
    @ResponseBody
    public Banner getBannerById(@PathVariable @NotBlank Long id) {
        System.out.println("我进来了");
        /**
         * jpa在这里属于懒加载，第一次查询时，它不会去查询与它相关联的集合数据，
         * 在需要的时候，才会去进行二次查询关联数据
         */
        Banner banner = bannerService.findOneById(id);
        return banner;
    }
}
