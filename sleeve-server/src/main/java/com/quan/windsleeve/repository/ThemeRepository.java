package com.quan.windsleeve.repository;

import com.quan.windsleeve.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme,Long> {

    /**
     * 通过 name 获取某一个theme主题,包含spu详情
     * @param name
     * @return
     */
    Theme findOneByName(String name);

    /**
     * 根据一组theme名称，查询一组Theme数据
     * @Query注解中的sql语句，不是标准的sql查询语句，它是操作Theme实体类的，所以当前的方法名
     * 也不需要严格按照jpa的语法规范来书写
     * @param names
     * 使用@Query注解进行查询时，内部注入的参数，需要使用@Param注解定义，否则会报错
     */
    @Query("select t from Theme t where t.name in (:names)")
    List<Theme> findThemeAndSpuGroupByNames(@Param("names") List<String> names);
}
