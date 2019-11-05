package com.itcast.mapper;

import com.itcast.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetmealMapper {
    void addSetmeal(Setmeal setmeal);

    void addSetmealAndCheckGroupRelation(List<Map> params);

    List<Setmeal> findPage(@Param("queryString") String queryString);
}
