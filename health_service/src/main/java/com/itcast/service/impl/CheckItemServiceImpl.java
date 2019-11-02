package com.itcast.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.mapper.CheckItemMapper;
import com.itcast.pojo.CheckItem;
import com.itcast.pojo.PageResult;
import com.itcast.pojo.QueryPageBean;
import com.itcast.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemMapper checkItemMapper;


    /**
     * 添加查询项
     * @param checkItem 前端传递来的参数
     */
    @Override
    public void add(CheckItem checkItem) {

        checkItemMapper.add(checkItem);
    }

    /**
     * 分页查询
     * 使用组件进行操作
     * @param queryPageBean 前端传递的分页查询条件
     * @return 分页结果集
     */
    @Override
    public PageResult queryPage(QueryPageBean queryPageBean) {

        //分页插件  传递的参数 当前页   当前页大小

        Page page  = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());


        List<CheckItem> list =  checkItemMapper.pageCheckItem(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 编辑检查项
     * @param checkItem 前端传递来编辑的检查项信息
     */
    @Override
    public void editCheck(CheckItem checkItem) {

        checkItemMapper.updateCheckItem(checkItem);
    }

    /**
     * 根据id查询检查项
     * @param id 前端传递的检查项id
     * @return 查询到的检查项实体
     */
    @Override
    public CheckItem findCheckById(Integer id) {
        return checkItemMapper.findCheckById(id);
    }

    /**
     * 根据id删除指定检查项
     * @param id 前端页面传递来的id属性
     */
    @Override
    public void deleteCheck(Integer id) {

        checkItemMapper.deleteCheck(id);
    }
}
