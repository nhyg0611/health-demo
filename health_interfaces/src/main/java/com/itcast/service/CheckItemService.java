package com.itcast.service;

import com.itcast.pojo.CheckItem;
import com.itcast.pojo.PageResult;
import com.itcast.pojo.QueryPageBean;
import exceptions.DeleteException;

import java.util.List;

public interface CheckItemService {

    public void add(CheckItem checkItem);

    PageResult queryPage(QueryPageBean queryPageBean);

    void editCheck(CheckItem checkItem);

    CheckItem findCheckById(Integer id);

    void deleteCheck(Integer id) throws DeleteException;

    List<CheckItem> findAll();
}
