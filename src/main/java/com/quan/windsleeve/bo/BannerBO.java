package com.quan.windsleeve.bo;

import com.quan.windsleeve.model.Spu;
import com.quan.windsleeve.model.Theme;
import lombok.Builder;
import lombok.Data;

/**
 * @Author Guiquan Chen
 * @Date 2021/7/22 14:52
 * @Version 1.0
 */
@Data
@Builder
public class BannerBO {

    Integer type;

    Spu spu;

    Theme theme;
}
