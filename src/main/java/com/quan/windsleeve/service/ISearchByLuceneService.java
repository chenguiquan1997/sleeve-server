package com.quan.windsleeve.service;

import com.quan.windsleeve.bo.LuceneIdsBO;
import com.quan.windsleeve.model.Spu;
import com.quan.windsleeve.vo.Paging;

import java.util.List;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/29 17:10
 * @Version 1.0
 */
public interface ISearchByLuceneService {

    LuceneIdsBO searchSimilarProducts(String title);

    Spu cacheTest(Long id);

    LuceneIdsBO getSimilarIdsFromEhcache(String text);

    Paging getSpusByKey(Integer start, Integer count, String text);
}
