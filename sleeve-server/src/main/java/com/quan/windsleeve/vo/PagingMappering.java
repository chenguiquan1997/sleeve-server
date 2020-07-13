package com.quan.windsleeve.vo;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建一个通用类，可以将服务端提供的分页数据，任意的在两个类型之间进行属性的拷贝
 * @param <T> 源对象
 * @param <K> 目标对象
 */
public class PagingMappering<T,K> extends Paging {

    public PagingMappering(Page<T> tPage , Class<K> kClass) {
        this.initPagingParam(tPage);
        //从服务端返回的Page对象
        List<T> sourceList = tPage.getContent();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        //创建一个目标集合
        List<K> targetListVO = new ArrayList<>();
        sourceList.forEach(source -> {
            K sourceVO = mapper.map(source,kClass);
            targetListVO.add(sourceVO);
        });
        this.setItems(targetListVO);
    }


}
