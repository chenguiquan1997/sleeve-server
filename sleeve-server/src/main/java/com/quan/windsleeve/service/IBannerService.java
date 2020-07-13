package com.quan.windsleeve.service;

import com.quan.windsleeve.model.Banner;

public interface IBannerService {

    public Banner findOneById(Long id);

    Banner findOneByName(String name);
}
