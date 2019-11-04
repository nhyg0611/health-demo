package com.itcast.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.pojo.CheckGroup;
import com.itcast.pojo.PageResult;
import com.itcast.pojo.QueryPageBean;
import com.itcast.pojo.Result;
import com.itcast.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;


    /**
     * 添加检查组
     * @param checkGroup 前端页面传递来的检查组信息 为json 格式
     * @return 返回结果集 前端页面进行判断显示 提示
     */
    @RequestMapping("/add")
    public Result addCheckGroup(@RequestBody CheckGroup checkGroup){

        try {
            checkGroupService.addCheckGroup(checkGroup);

            return Result.success("添加成功");
        }catch (Exception e){

            return Result.error("添加失败");
        }
    }

    /**
     * 分页查询操作
     * @param queryPageBean  前端页面传递来的分页对象信息
     * @return 分页的结果集 总页数 以及当页显示的集合信息
     */
    @RequestMapping("/findpage")
    public PageResult findGroupPage(@RequestBody QueryPageBean queryPageBean){

       return checkGroupService.findGroupPage(queryPageBean);
    }

    /**
     * 根据id查询当前检查组信息
     * @param id 前端页面scope 绑定的id信息
     * @return 返回map 用来返回所需要的全部信息
     * 所有的检查项 以及被选中的检查项id 以及当前检查组的详细信息
     */
    @RequestMapping("/findbyid")
    public Result findCheckGroupById(Integer id){

        try {
            Map map = checkGroupService.findCheckGroupById(id);
            return Result.success("查询成功",map);
        }catch (Exception e){
            return Result.error("编辑查询失败");
        }


    }

    /**
     * 编辑保存的方法
     * @param checkGroup 前端页面编辑过后的检查组信息
     * @return 返回结果集 判断是否编辑成功
     */
    @RequestMapping("/edit")
    public Result editCheckGroup(@RequestBody CheckGroup checkGroup){

        try {
            checkGroupService.editCheckGroup(checkGroup);
            return Result.success("编辑成功");
        }catch (Exception e){

            return Result.error("编辑失败");
        }

    }

    @RequestMapping("/deletebyid")
    public Result deleteCheckGroupById(Integer id){

        try {
            checkGroupService.deleteCheckGroupById(id);
            return Result.success("删除成功");

        }catch (Exception e){

            return Result.error("删除失败");
        }
    }
}
