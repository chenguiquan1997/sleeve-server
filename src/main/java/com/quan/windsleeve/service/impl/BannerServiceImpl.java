package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.model.Banner;
import com.quan.windsleeve.repository.BannerRepository;
import com.quan.windsleeve.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl implements IBannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Banner findOneById(Long id) {
        return bannerRepository.findOneById(id);
    }

    @Override
    public Banner findOneByName(String name) {
        return bannerRepository.findOneByName(name);
    }
}
