package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner,Long> {

    public Banner findOneById(Long id);

    /**
     * 根据banner name 查询一组数据
     * @param name
     * @return Banner
     */
    Banner findOneByName(String name);
}
