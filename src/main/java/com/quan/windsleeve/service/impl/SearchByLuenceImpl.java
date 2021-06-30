package com.quan.windsleeve.service.impl;

import com.hankcs.lucene.HanLPAnalyzer;
import com.quan.windsleeve.exception.http.LuenceQueryException;
import com.quan.windsleeve.service.ISearchByLuceneService;
import com.quan.windsleeve.service.ISpuService;
import lombok.Data;
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
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    @Value("${luence.search-count}")
    private Integer searchCount;

    @Value("${luence.path}")
    private String path;

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
    @Cacheable
    @CachePut
    @Override
    public List<Long> searchSimilarProducts(String text) {
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
        return ids;
    }
}
