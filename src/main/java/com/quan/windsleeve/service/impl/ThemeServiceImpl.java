package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.model.Theme;
import com.quan.windsleeve.repository.ThemeRepository;
import com.quan.windsleeve.service.IThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeServiceImpl implements IThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public Theme findOneByName(String name) {
        return themeRepository.findOneByName(name);
    }

    @Override
    public Theme findOneById(Long id) {
        return themeRepository.findOneById(id);
    }

    public String findById(Long id) {
        Theme theme = themeRepository.findOneById(id);
        if(theme != null) {
            return theme.getName();
        }
        return "";
    }

    @Override
    public List<Theme> findThemeAndSpuGroupByNames(List<String> names) {
        return themeRepository.findThemeAndSpuGroupByNames(names);
    }
}
