package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.mapper.SetmealMapper;
import com.itcast.pojo.PageResult;
import com.itcast.pojo.QueryPageBean;
import com.itcast.pojo.Setmeal;
import com.itcast.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;


    @Override
    public void addSetmeal(Setmeal setmeal) {

        //先将基本的信息进行插入的操作
        setmealMapper.addSetmeal(setmeal);
        //通过主键回显来 的到该套餐的id值 然后进行关联表的建立连接的操作
        Integer setmealId = setmeal.getId();
        List<Integer> checkgroupIds = setmeal.getCheckgroupIds();
        List<Map> params = new ArrayList<>();

        for (Integer checkgroupId : checkgroupIds) {
            Map map = new HashMap();
            map.put("setmeal_id",setmealId);
            map.put("checkgroup_id",checkgroupId);
            params.add(map);
        }

        setmealMapper.addSetmealAndCheckGroupRelation(params);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {

        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        List<Setmeal> list = setmealMapper.findPage(queryPageBean.getQueryString());

        return  new PageResult(page.getTotal(),list);
    }
}
