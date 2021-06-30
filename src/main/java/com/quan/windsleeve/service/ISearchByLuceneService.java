package com.quan.windsleeve.service;

import java.util.List;

/**
 * @Author Guiquan Chen
 * @Date 2021/6/29 17:10
 * @Version 1.0
 */
public interface ISearchByLuceneService {

    List<Long>  searchSimilarProducts(String title);
}
