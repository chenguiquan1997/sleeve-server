package com.quan.windsleeve.service;

import com.quan.windsleeve.bo.BannerBO;
import com.quan.windsleeve.model.Banner;

public interface IBannerService {

    public Banner findOneById(Long id);

    Banner findOneByName(String name);

    BannerBO findBannerItem(Integer type, Long id);
}
