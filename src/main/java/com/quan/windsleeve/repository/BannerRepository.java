package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner,Long> {
    public Banner findOneById(Long id);
}
