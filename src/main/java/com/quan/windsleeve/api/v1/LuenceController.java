package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.bo.LuceneIdsBO;
import com.quan.windsleeve.model.Spu;
import com.quan.windsleeve.service.ISearchByLuceneService;
import com.quan.windsleeve.service.ISpuService;
import com.quan.windsleeve.util.SpringContextUtils;
import com.quan.windsleeve.vo.Paging;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.web.bind.annotation.*;

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


}
