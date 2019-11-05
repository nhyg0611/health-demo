package com.itcast.mapper;

import com.itcast.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CheckGroupMapper {

    void addCheckGroup(CheckGroup checkGroup);

    void addGroupRelation(List<Map> listmap);

    List<CheckGroup> queryPage(@Param("queryString") String queryString);

    CheckGroup findGroupById(@Param("id") Integer id);

    List<Integer> findItemsById(@Param("id") Integer id);

    void editCheckGroup(CheckGroup checkGroup);

    void deleteItems(@Param("id") Integer id);

    void deleteCheckGroup(@Param("id") Integer id);

    List<CheckGroup> findAll();
}
