package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.core.annotation.ScopeLevel;
import com.quan.windsleeve.exception.http.NotFoundException;
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

    @GetMapping("/name")
    @ResponseBody
    @ScopeLevel(8)
    public Banner getBannerById(@RequestParam Long id) {
        Banner banner = bannerService.findOneById(id);
        return banner;
    }

    @GetMapping("/name/{name}")
    @ResponseBody
    @ScopeLevel
    public Banner findOneByName(@PathVariable String name) {
        Banner banner = bannerService.findOneByName(name);
        if (banner == null) {
            throw new NotFoundException(20001);
        }
        return banner;
    }
}