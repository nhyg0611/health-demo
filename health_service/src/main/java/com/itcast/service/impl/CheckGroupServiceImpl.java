package com.itcast.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itcast.mapper.CheckGroupMapper;
import com.itcast.mapper.CheckItemMapper;
import com.itcast.pojo.CheckGroup;
import com.itcast.pojo.CheckItem;
import com.itcast.pojo.PageResult;
import com.itcast.pojo.QueryPageBean;
import com.itcast.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sun.security.action.PutAllAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkGroupMapper;

    @Autowired
    private CheckItemMapper checkItemMapper;


    /**
     * 添加检查组的方法
     * @param checkGroup 前端页面传递来的检查组信息
     */
    @Override
    public void addCheckGroup(CheckGroup checkGroup) {

        checkGroupMapper.addCheckGroup(checkGroup);

        //sql 语句中开启主键回显的功能 不然无法得到当前插入语句的id 值
        Integer groupId = checkGroup.getId();

        //根据前端
        List<Integer> checkitemIds = checkGroup.getCheckitemIds();

      setRelation(groupId,checkitemIds);
    }

    /**
     * 向关联表中添加数据的方法
     * @param groupId 需要关联的检查组id
     * @param checkitemIds 需要关联的检查项集合id
     */
    //添加关联表数据的方法
    private void setRelation(Integer groupId,List<Integer> checkitemIds){

        if (CollectionUtil.isNotEmpty(checkitemIds)){
            List<Map> listmap = new ArrayList<>();
            for (Integer checkitemId : checkitemIds) {

                Map map = new HashMap();

                map.put("checkgroup_id",groupId);
                map.put("checkitem_id",checkitemId);
                listmap.add(map);

            }
            checkGroupMapper.addGroupRelation(listmap);
        }

    }

    /**
     * 根据分页对象 向分页插件中进行注入 得到分页的结果
     * @param queryPageBean 前端页面传递的分页属性
     * @return 分页的结果集
     */
    @Override
    public PageResult findGroupPage(QueryPageBean queryPageBean) {


        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        List<CheckGroup> list = checkGroupMapper.queryPage(queryPageBean.getQueryString());

        return new PageResult(page.getTotal(),list);
    }

    /**
     * 将前端一次请求查询出想要的全部信息
     * @param id 前端传递来的id
     * @return  将检查项 检查组 封装为map 进行返回
     */
    @Override
    public Map findCheckGroupById(Integer id) {

        //全部查询项的信息
        List<CheckItem> tableData = checkItemMapper.findAll();

        Map map = new HashMap();

        map.put("tableData",tableData);

        //根据检查组id查询组的信息
        CheckGroup formData = checkGroupMapper.findGroupById(id);

        map.put("formData",formData);

        //根据id查询关联表中满足其中的id并返回

        List<Integer> checkitemIds=checkGroupMapper.findItemsById(id);

        map.put("checkitemIds",checkitemIds);

        return map;

    }

    /**
     * 编辑检查组的方法
     * @param checkGroup 前端页面传递来的检查组信息
     */
    @Override
    public void editCheckGroup(CheckGroup checkGroup) {

        //首先根据当前信息 修改原有的检查组中的信息

        checkGroupMapper.editCheckGroup(checkGroup);

        //在将原有检查组中的检查项进行删除的操作
        checkGroupMapper.deleteItems(checkGroup.getId());

        //在根据检查组id 和前端页面传递来的items属性 再次进行关联的建立操作

        setRelation(checkGroup.getId(),checkGroup.getCheckitemIds());
    }

    /**
     * 根据id删除指定的检查组
     * @param id 前端传递来的检查组id
     *
     */
    @Override
    public void deleteCheckGroupById(Integer id) {

        //首先和当前检查组id 有关的关联数据库进行删除的操作

        checkGroupMapper.deleteItems(id);

        //在把当前的检查组对象进行删除

        checkGroupMapper.deleteCheckGroup(id);
    }


    @Override
    public List<CheckGroup> findAll() {

        return checkGroupMapper.findAll();
    }
}
