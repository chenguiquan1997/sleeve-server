package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.model.HotSearch;
import com.quan.windsleeve.service.IHotSearchService;
import com.quan.windsleeve.service.ISearchByLuceneService;
import com.quan.windsleeve.service.ISpuService;
import com.quan.windsleeve.vo.HotSearchVO;
import com.quan.windsleeve.vo.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/29 17:58
 * @Version 1.0
 */
@RestController
@RequestMapping("/lucene/key")
public class LuenceController {

    @Autowired
    private ISpuService spuService;

    @Autowired
    private ISearchByLuceneService luceneService;

    @Autowired
    private IHotSearchService hotSearchService;

    @GetMapping("/spu")
    public Paging getSpusByKey(@RequestParam Integer start,
                               @RequestParam Integer count,
                               @RequestParam String keyword) {
        return luceneService.getSpusByKey(start,count,keyword);
    }

//    @GetMapping("/spu")
//    public Spu getSpusByKey(@RequestParam Long id) {
//        EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringContextUtils
//                .getBean("ehcache1");
//        CacheManager cacheManager1 = cacheManager.getCacheManager();
//        Cache cache = cacheManager1.getCache("local");
//        List<Object> list = cache.getKeys();
//        System.out.println(list.toString());
//        return luceneService.cacheTest(id);
//    }

    /**
     * @Description: 获取前10个最热门的搜索关键字
     * @return java.util.List<java.lang.String>
     * @Author: Guiquan Chen
     * @Date: 2021/8/2
     */
    @GetMapping("/top10")
    public List<String> getHotSearchTop10() {
        return hotSearchService.getkeywordTop10();
    }

    @PostMapping("/add/hot/record")
    public void addHotSearchRecord(@RequestParam("keyword") @NotEmpty String keyword) {
        hotSearchService.addHotProductrecords(keyword);
    }

}
