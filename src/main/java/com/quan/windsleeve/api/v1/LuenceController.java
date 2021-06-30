package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.service.ISearchByLuceneService;
import com.quan.windsleeve.service.ISpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/29 17:58
 * @Version 1.0
 */
@RestController
@RequestMapping("/luence/key")
public class LuenceController {

    @Autowired
    private ISpuService spuService;

    @Autowired
    private ISearchByLuceneService luceneService;

    @GetMapping("/spu")
    public void getSpusByKey(@RequestParam String keyword) {
        List<Long> ids = luceneService.searchSimilarProducts(keyword);

    }
}
