package com.quan.windsleeve.api.v1;


import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.quan.windsleeve.bo.PageCounter;
import com.quan.windsleeve.exception.http.NotFoundException;
import com.quan.windsleeve.model.Category;
import com.quan.windsleeve.model.Spu;
import com.quan.windsleeve.service.ISpuService;
import com.quan.windsleeve.util.CommonUtils;
import com.quan.windsleeve.vo.Paging;
import com.quan.windsleeve.vo.PagingMappering;
import com.quan.windsleeve.vo.SpuSimplifyVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/spu")
public class SpuController {

    @Autowired
    private ISpuService spuService;

    /**
     * @Positive 注解可以校验参数的正负性，如果是负数，那么会抛出异常
     * @param id
     * @return
     */
    @GetMapping("/id/{id}/detail")
    public Spu getSpuDetailById(@PathVariable @Positive Long id) {
        Spu spuDetail = spuService.findSpuDetailById(id);
        if(spuDetail == null) {
            throw new NotFoundException(60001);
        }
        return spuDetail;
    }
    @GetMapping("id/{id}/detail/vo")
    public SpuSimplifyVO getSpuByVO(@PathVariable @Positive Long id) {
        Spu spu = spuService.findSpuDetailById(id);
        if(spu == null) {
            throw new NotFoundException(60001);
        }
        SpuSimplifyVO spuSimplifyVO = new SpuSimplifyVO();
        BeanUtils.copyProperties(spu,spuSimplifyVO);
        return spuSimplifyVO;
    }
    // /latest?start=xxx&count=xx
    @GetMapping("/latest")
    public PagingMappering getSpuPageList(@RequestParam(defaultValue = "0") Integer start,
                                @RequestParam(defaultValue = "2") Integer count) {

        PageCounter pageCounter = CommonUtils.PageCounterConvert(start,count);
        Page<Spu> spuPage = spuService.findSpuPageList(pageCounter.getPageNum(),pageCounter.getPageSize());
        if(spuPage == null) {
            return null;
        }
        return new PagingMappering(spuPage,SpuSimplifyVO.class);
    }

    @GetMapping("/latest/test")
    public Paging getSpuPageListTest(@RequestParam(defaultValue = "0") Integer start,
                                          @RequestParam(defaultValue = "2") Integer count) {

        PageCounter pageCounter = CommonUtils.PageCounterConvert(start,count);
        Page<Spu> spuPage = spuService.findSpuPageList(pageCounter.getPageNum(),pageCounter.getPageSize());
        if(spuPage == null) {
            return null;
        }
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<SpuSimplifyVO> spuVOList = new ArrayList<>();
        Paging paging = new Paging(spuPage);
        List<Spu> spuList = spuPage.getContent();
        spuList.forEach(spu -> {
            SpuSimplifyVO spuSimplifyVO = new SpuSimplifyVO();
            BeanUtils.copyProperties(spu,spuSimplifyVO);
            spuVOList.add(spuSimplifyVO);

        });
        paging.setItems(spuVOList);
        return paging;
    }



}
