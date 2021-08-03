package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.model.HotSearch;
import com.quan.windsleeve.repository.HotSearchRepository;
import com.quan.windsleeve.service.IHotSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Guiquan Chen
 * @Date 2021/8/2 15:14
 * @Version 1.0
 */
@Service
public class HotSearchServiceImpl implements IHotSearchService {

    private static final Logger log = LoggerFactory.getLogger(HotSearchServiceImpl.class);

    @Autowired
    private HotSearchRepository hotSearchRepository;

    @Override
    public List<String> getkeywordTop10() {
        List<HotSearch> hotSearches = hotSearchRepository.findAll(Sort.by(Sort.Direction.DESC,"count"));
        if(hotSearches.size() > 10) {
            hotSearches = hotSearches.subList(0,10);
        }
        List<String> hots = new ArrayList<>();
        if(hotSearches != null) {
            hotSearches.forEach(hotSearch -> {
                hots.add(hotSearch.getHotKeyword());
            });
        }
        return hots;
    }

    @Override
    public void addHotProductrecords(String keyword) {
        try {
            // 先查询一下，如果表中有当前key，那么直接修改count
            HotSearch hot = hotSearchRepository.findByHotKeyword(keyword);
            // 如果没有，那么需要重新添加
            if(hot == null) {
                HotSearch hotSearch = HotSearch.builder().hotKeyword(keyword).count(1).build();
                hotSearchRepository.save(hotSearch);
            }
            hotSearchRepository.updateCountByKeyword(keyword);
        }catch (Exception e) {
            e.printStackTrace();
            log.warn("添加热门商品搜索记录时失败，keyword=[{}]",keyword);
        }

    }
}
