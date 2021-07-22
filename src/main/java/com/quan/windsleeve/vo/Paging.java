package com.quan.windsleeve.vo;

import com.quan.windsleeve.model.Spu;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 前端瀑布流功能在进行分页查询时，后端Controller层需要给前端返回的对象
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Paging {

    private Integer count;
    private Long total;
    private Integer page;
    private Integer totalPage;
    private Boolean isLast;
    private List items;


    public Paging(Page page) {
        this.initPagingParam(page);

    }

    public void initPagingParam(Page page) {
        this.total = page.getTotalElements();
        this.page = page.getNumber();
        this.count = page.getSize();
        this.totalPage = page.getTotalPages();
        this.isLast = page.isLast();
    }

    public static List<SpuSimplifyVO> convert(List<Spu> spuList) {
        List<SpuSimplifyVO> spuSimplifyVOS = new ArrayList<>();
        if(spuList == null || spuList.size() < 1) return spuSimplifyVOS;
        spuList.forEach(spu -> {
            SpuSimplifyVO spuSimplifyVO = new SpuSimplifyVO();
            BeanUtils.copyProperties(spu,spuSimplifyVO);
            spuSimplifyVOS.add(spuSimplifyVO);
        });
        return spuSimplifyVOS;
    }
}
