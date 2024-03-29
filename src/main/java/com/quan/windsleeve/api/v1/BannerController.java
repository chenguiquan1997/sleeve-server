package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.bo.BannerBO;
import com.quan.windsleeve.core.annotation.ScopeLevel;
import com.quan.windsleeve.exception.http.NotFoundException;
import com.quan.windsleeve.model.Banner;
import com.quan.windsleeve.service.IBannerService;
import com.quan.windsleeve.vo.BannerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    /**
     * 根据Banner id 查询某一条Banner的信息
     * @param id
     * @return
     */
    @GetMapping("/name")
    @ResponseBody
    public Banner getBannerById(@RequestParam Long id) {
        Banner banner = bannerService.findOneById(id);
        return banner;
    }

    /**
     * 根据Banner name 查询某一条Banner的信息
     * @param name
     * @return
     */
    @GetMapping("/name/{name}")
    @ResponseBody
    //@ScopeLevel
    public Banner findOneByName(@PathVariable String name) {
        Banner banner = bannerService.findOneByName(name);
        if (banner == null) {
            throw new NotFoundException(20001);
        }
        return banner;
    }

    @GetMapping("/item")
    @ResponseBody
    public BannerVO findBannerItem(@NotNull @Positive @RequestParam("type") Integer type,
                                   @NotNull @Positive @RequestParam("key") Long id) {
        BannerBO bannerBO = bannerService.findBannerItem(type,id);
        return BannerVO.convert(bannerBO);
    }
}