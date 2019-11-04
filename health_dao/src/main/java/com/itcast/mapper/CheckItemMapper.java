package com.itcast.mapper;

import com.itcast.pojo.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckItemMapper {

    /**
     * map & pojo 不需要加@Param
     * 多参数建议加@Param ,不加就需要按照param1 param2 ...paramN
     * List & Array 可以不加@Param  如果不加@Param取值需要写list&array
     * 如果有多个List参数那么取值  param1 param2 ...paramN
     */

    public void  add(CheckItem checkItem);

    List<CheckItem> pageCheckItem(@Param("queryString")String queryString);

    CheckItem findCheckById(@Param("id") Integer id);

    void updateCheckItem(CheckItem checkItem);

    void deleteCheck(@Param("id") Integer id);

    Integer findRelationById(@Param("id")Integer id);

    List<CheckItem> findAll();
}
