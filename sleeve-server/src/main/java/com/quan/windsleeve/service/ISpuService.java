package com.quan.windsleeve.service;

import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.model.Spu;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISpuService {

    Spu findSpuDetailById(Long id);

    Page<Spu> findSpuPageList(Integer pageNum,Integer pageSize);


}
