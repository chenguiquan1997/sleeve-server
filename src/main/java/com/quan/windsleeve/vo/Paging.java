package com.quan.windsleeve.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 前端瀑布流功能在进行分页查询时，后端Controller层需要给前端返回的对象
 */

@NoArgsConstructor
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
        System.out.println("total=> " + this.total);
        this.page = page.getNumber();
        System.out.println("page=> " + this.page);
        this.count = page.getSize();
        System.out.println("count=> " + this.count);
        this.totalPage = page.getTotalPages();
        System.out.println("totalPage=> "+ this.totalPage);
        this.isLast = page.isLast();
    }

}
