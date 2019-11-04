package com.itcast.service;

import com.itcast.pojo.CheckGroup;
import com.itcast.pojo.PageResult;
import com.itcast.pojo.QueryPageBean;

import java.util.Map;

public interface CheckGroupService {

    void addCheckGroup(CheckGroup checkGroup);

    PageResult findGroupPage(QueryPageBean queryPageBean);

    Map findCheckGroupById(Integer id);

    void editCheckGroup(CheckGroup checkGroup);

    void deleteCheckGroupById(Integer id);
}
