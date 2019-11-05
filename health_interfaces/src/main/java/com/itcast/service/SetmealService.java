package com.itcast.service;

import com.itcast.pojo.PageResult;
import com.itcast.pojo.QueryPageBean;
import com.itcast.pojo.Setmeal;

public interface SetmealService {

    void addSetmeal(Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);
}
