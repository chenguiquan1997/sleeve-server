package com.quan.windsleeve.service;

import com.quan.windsleeve.model.Theme;

import java.util.List;

public interface IThemeService {
    /**
     * 通过 name 获取某一个theme主题
     * @param name
     * @return
     */
    Theme findOneByName(String name);

    /**
     * 根据一组主题名，查询一组Theme
     * @param names
     * @return
     */
    List<Theme> findThemeAndSpuGroupByNames(List<String> names);
}
