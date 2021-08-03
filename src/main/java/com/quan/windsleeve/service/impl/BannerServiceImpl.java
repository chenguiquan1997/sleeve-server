package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.bo.BannerBO;
import com.quan.windsleeve.core.enums.BannerItemType;
import com.quan.windsleeve.exception.http.NotFoundException;
import com.quan.windsleeve.model.Banner;
import com.quan.windsleeve.model.Spu;
import com.quan.windsleeve.model.Theme;
import com.quan.windsleeve.repository.BannerRepository;
import com.quan.windsleeve.repository.SpuRepository;
import com.quan.windsleeve.repository.ThemeRepository;
import com.quan.windsleeve.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.quan.windsleeve.core.enums.BannerItemType.SPU;

@Service
public class BannerServiceImpl implements IBannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private SpuRepository spuRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ThemeServiceImpl themeService;

    @Override
    public Banner findOneById(Long id) {
        return bannerRepository.findOneById(id);
    }

    @Override
    public Banner findOneByName(String name) {
        return bannerRepository.findOneByName(name);
    }

    @Override
    public BannerBO findBannerItem(Integer type, Long id) {

        switch (BannerItemType.toType(type)) {
            case SPU:
                // 查询指定SPU数据
                Spu spu = spuRepository.findOneById(id);
                if(spu != null) {
                    // 简化数据
                    return BannerBO.builder().spu(spu).type(type).build();
                }
                break;
            case THEME:
                // 查询 主题
                Theme theme = themeService.findOneById(id);
                if(theme != null) {
                    return BannerBO.builder().type(type).theme(theme).build();
                }
                break;
            case SPU_LIST:
                // 查询 spu 列表
            default:
                throw new NotFoundException(20002);
        }
        return null;
    }
}
