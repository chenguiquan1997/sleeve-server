package com.quan.windsleeve.service.impl;

import com.hankcs.lucene.HanLPAnalyzer;
import com.quan.windsleeve.bo.LuceneIdsBO;
import com.quan.windsleeve.exception.http.LuenceQueryException;
import com.quan.windsleeve.model.Spu;
import com.quan.windsleeve.service.ISearchByLuceneService;
import com.quan.windsleeve.service.ISpuService;
import com.quan.windsleeve.util.SpringContextUtils;
import com.quan.windsleeve.vo.Paging;
import com.quan.windsleeve.vo.SpuSimplifyVO;
import lombok.Data;
import net.sf.ehcache.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/29 17:10
 * @Version 1.0
 */

@Service
public class SearchByLuenceImpl implements ISearchByLuceneService {

    private static final Logger log = LoggerFactory.getLogger(SearchByLuenceImpl.class);

    private static final String CACHE_NAME="local";

    @Value("${luence.search-count}")
    private Integer searchCount;

    @Value("${luence.path}")
    private String path;

    @Autowired
    private ISpuService spuService;

    /**
     * 注解中的value属性，一定要与ehcache.xml文件中的某一个cache相匹配，也就是与cache标签中的name字段相同
     * @CachePut 注解表示当前的方法执行后，需要将返回的结果值，以键值对的形式存储到指定的缓存中。
     * @Cacheable 注解表示当前的方法支持从缓存查询，当调用该方法之前，需要优先从缓存中查询
     * @CachePut与@Cacheable的区别： @CachePut不会先读取缓存中的数据，而是首先执行方法体，@Cacheable会首先读取缓存中的数据
     * 因为Spring的cache功能是以key-value的形式来存储数据的，所以key属性充当数据的key，结果值充当数据的value
     */

    /**
     * @Description: 根据关键字，在Lucene索引中查询商品id
     * @param text 前端输入关键字
     * @return java.util.List<java.lang.Long>
     * @Author: Guiquan Chen
     * @Date: 2021/6/30
     */
    @Override
    public LuceneIdsBO searchSimilarProducts(String text) {
       // 先从缓存中查询，如果没有，走Lucene中查询
//        List<Long> ids = getSimilarIdsFromEhcache(text);
//        System.out.println(ids);
//        if(ids == null) {
//            ids = writeSimilarIdsToEhcache(text);
//            EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringContextUtils
//                    .getBean("ehcache1");
//            CacheManager cacheManager1 = cacheManager.getCacheManager();
//            Cache cache = cacheManager1.getCache("local");
//            List<Object> list = cache.getKeys();
//            System.out.println();
//        }

        LuceneIdsBO ids = getSimilarIdsFromEhcache(text);
        System.out.println(ids);
        EhCacheCacheManager cacheManager = (EhCacheCacheManager) SpringContextUtils
                .getBean("ehcache1");
        CacheManager cacheManager1 = cacheManager.getCacheManager();
        Cache cache = cacheManager1.getCache("local");
        List<Object> list = cache.getKeys();
        System.out.println(list.toString());


        return ids;
    }

    /**
     * @Description: 先在ehcache缓存中查询
     * @param text
     * @return java.util.List<java.lang.Long>
     * @Author: Guiquan Chen
     * @Date: 2021/7/5
     */
    @Cacheable(value = CACHE_NAME, key = "#text")
    public LuceneIdsBO getSimilarIdsFromEhcache(String text) {
        System.out.println("缓存中没有查询到");
        System.out.println("进入Lucene索引中查找");
        List<Long> ids = new ArrayList<>();
        try {
            // 打开索引目录
            Directory directory = FSDirectory.open(Paths.get(this.path));
            // 创建索引阅读器
            IndexReader reader = DirectoryReader.open(directory);
            // 创建索引查询器
            IndexSearcher searcher = new IndexSearcher(reader);
            // 需要搜索的字段
            String field="title";
            Analyzer analyzer = new HanLPAnalyzer();
            // 构建查询解析器，包括需要解析哪一个字段，以及具体的解析器
            QueryParser parser = new QueryParser(field, analyzer);
            // 将用户从前端属于的关键字进行 中文拆分
            Query query = parser.parse(text);
            TopDocs docs = searcher.search(query, this.searchCount);
            log.info("搜索关键词：[{}], 命中的记录数：[{}]",text,docs.totalHits);
            ScoreDoc[] array = docs.scoreDocs;
            for (ScoreDoc one : array) {
                Document document = searcher.doc(one.doc);
                Long id = Long.valueOf(document.get("id"));
                ids.add(id);
            }
            reader.close();
            directory.close();
        }
        catch (Exception e) {
            log.error("luence 进行分词时，出现异常，keyText=[{}]",text,e);
            throw new LuenceQueryException(60002);
        }
        return LuceneIdsBO.builder().ids(ids).build();
    }

    @Override
    public Paging getSpusByKey(Integer start, Integer count, String text) {
        LuceneIdsBO luceneIdsBO = this.getSimilarIdsFromEhcache(text);
        if(luceneIdsBO == null || luceneIdsBO.getIds().size() < 1) return new Paging(0, (long) 0,0,0,true,new ArrayList());
        List<Long> ids = luceneIdsBO.getIds();
        // 数据总量
        Integer total = ids.size();
        Integer finalCount = start + count;
        // 判断是否有数据
        Boolean isLeast = false;
        if(finalCount >= total) {
           isLeast = true;
        }
        // 当前页
        Integer currPage = (start + 1) % count == 0 ? (start + 1) / count : (start + 1) / count + 1;
        // 总页数
        Integer totalPage = total % count == 0 ? total / count : total / count + 1;
        // 需要从数组中截取当前需要查询的id
        List<Long> subIds = ids.subList(start,start + count >= total? total : start + count);
        List<Spu> spuList = spuService.findSpuListBykeyWord(subIds);
        List<SpuSimplifyVO> spuSimplifyVOS = Paging.convert(spuList);
        return new Paging(count,total.longValue(),currPage,totalPage,isLeast,spuSimplifyVOS);
    }



    @Cacheable(value = CACHE_NAME,key = "#id")
    public Spu cacheTest(Long id) {
        System.out.println("进入spu");
        Spu spu = new Spu();
        spu.setId(id);
        spu.setDescription("测试");
        return spu;
    }

}
