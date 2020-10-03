package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.model.Sku;
import com.quan.windsleeve.service.ISkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private ISkuService skuService;

    @GetMapping("")
    public List<Sku> getSkuListByIds(@RequestParam(name = "ids") String ids) {
        if(ids==null || ids.isEmpty()){
            return Collections.emptyList();
        }
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
        List<Sku> skus = skuService.findSkuListBySkuId(idList);
        return skus;
    }
}
