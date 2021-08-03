package com.quan.windsleeve.vo;

import com.quan.windsleeve.bo.BannerBO;
import com.quan.windsleeve.core.enums.BannerItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Guiquan Chen
 * @Date 2021/7/22 14:42
 * @Version 1.0
 * 轮播图页面使用的Banner数据
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerVO<T> {

    /**
     * banner item 的所属类型：商品，主题，商品列表
     */
    Integer type;
    /**
     * banner-item 数据
     */
    T item;

    public static BannerVO convert(BannerBO bannerBO) {
        if(bannerBO == null) return new BannerVO();
        switch (BannerItemType.toType(bannerBO.getType())) {
            case SPU:
               return BannerVO.builder().type(bannerBO.getType())
                       .item(bannerBO.getSpu()).build();
            case THEME:
                return BannerVO.builder().type(bannerBO.getType())
                        .item(bannerBO.getTheme()).build();
            case SPU_LIST:
            default:
                return new BannerVO();
        }
    }
}
