package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.model.Spu;
import com.quan.windsleeve.repository.SpuRepository;
import com.quan.windsleeve.service.ISpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpuServiceImpl implements ISpuService {

    @Autowired
    private SpuRepository spuRepository;

    @Override
    public Spu findSpuDetailById(Long id) {
       Spu spuDetail = spuRepository.findOneById(id);
       return spuDetail;
    }

    @Override
    public Page<Spu> findSpuPageList(Integer pageNum,Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize, Sort.by("createTime").descending());
        return spuRepository.findAll(pageable);
    }

    @Override
    public Page<Spu> findSpuListByCategoryId(Integer pageNum, Integer pageSize, Integer categoryId, Boolean isRoot) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        if(isRoot == true) {
            return spuRepository.findByRootCategoryIdOrderByCreateTimeDesc(pageable,categoryId);
        }
        return spuRepository.findByCategoryIdOrderByCreateTimeDesc(pageable,categoryId);
    }

    @Override
    public Page<Spu> findSpuListByLuenceIds(Integer pageNum, Integer pageSize, List<Long> ids) {
        Pageable pageable = PageRequest.of(pageNum,pageSize, Sort.by("createTime").descending());

        return null;
    }

    /**
     * 根据关键字查询spu列表
     * @param ids
     * @return
     */
    @Override
    public List<Spu> findSpuListBykeyWord(List<Long> ids) {
        return spuRepository.findAllById(ids);
    }


}
