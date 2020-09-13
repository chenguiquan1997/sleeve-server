package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.exception.http.ParamNullException;
import com.quan.windsleeve.model.Theme;
import com.quan.windsleeve.service.impl.ThemeServiceImpl;
import com.quan.windsleeve.vo.ThemeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    private ThemeServiceImpl themeService;

    /**
     * 根据name，查询单一的一组Theme数据,附带spu详情
     * @param names
     * @return
     */
    @GetMapping("/by/one/names")
    public ThemeVO getThemeByName(@RequestParam String names) {
        if(names == null || names == ""){
            throw new ParamNullException(30001);
        }
        Theme theme = themeService.findOneByName(names);
        ThemeVO themeVO = new ThemeVO();
        BeanUtils.copyProperties(theme,themeVO);
        return themeVO;
    }

    /**
     * 根据一组主题 name，查询一组Theme
     * @param names
     * @return List<ThemeVO>
     */
    @GetMapping("/by/names")
    public List<ThemeVO> getThemeListByNames(@RequestParam String names) {
        //将names按照逗号进行拆分
        List<String> nameList =  Arrays.asList(names.split(","));
        System.out.println(nameList.toString());
        List<Theme> themeList = themeService.findThemeAndSpuGroupByNames(nameList);
        List<ThemeVO> themeVOList = new ArrayList<>();
        themeList.forEach(theme -> {
            ThemeVO themeVO = new ThemeVO();
            BeanUtils.copyProperties(theme,themeVO);
            themeVOList.add(themeVO);
        });
        return themeVOList;
    }

    /**
     * 查询和当前Theme主题相关的所有Spu
     * @param name
     * @return
     */
    @GetMapping("/name/{name}/with_spu")
    public Theme getThemeAndSpuByName(@PathVariable String name) {

        return themeService.findOneByName(name);
    }
}
