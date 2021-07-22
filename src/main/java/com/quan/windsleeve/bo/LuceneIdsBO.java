package com.quan.windsleeve.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Guiquan Chen
 * @Date 2021/7/6 10:01
 * @Version 1.0
 * 用于承装关键字搜索后，在Lucene倒排索引中筛选出来的 product id
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LuceneIdsBO implements Serializable {

    List<Long> ids;
}
