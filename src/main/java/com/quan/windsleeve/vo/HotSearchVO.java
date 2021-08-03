package com.quan.windsleeve.vo;

import com.quan.windsleeve.model.HotSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Guiquan Chen
 * @Date 2021/8/2 16:06
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotSearchVO {

    /**
     * 热门关键字
     */
    private String hotKeyword;

    public static List<HotSearchVO> convert(List<HotSearch> hotSearches) {
        List<HotSearchVO> hotSearchVOS = new ArrayList<>();
        if(hotSearches == null || hotSearches.size() < 1) return hotSearchVOS;
        hotSearches.forEach(hotSearch -> {
            HotSearchVO hotSearchVO = HotSearchVO.builder().hotKeyword(hotSearch.getHotKeyword()).build();
            hotSearchVOS.add(hotSearchVO);
        });
        return hotSearchVOS;
    }
}
