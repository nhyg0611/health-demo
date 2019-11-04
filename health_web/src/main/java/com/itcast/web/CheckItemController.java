package com.itcast.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.constant.MessageConstant;
import com.itcast.pojo.CheckItem;
import com.itcast.pojo.PageResult;
import com.itcast.pojo.QueryPageBean;
import com.itcast.pojo.Result;
import com.itcast.service.CheckItemService;
import exceptions.DeleteException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/check")
public class CheckItemController {
    /**
     * 页面传的是json数据，后端使用map 或者 pojo时 需要加@RequestBody
     * 基本类型 & 数组 & MultipartFile 只要保持页面的参数名称和controller方法形参一致就不用加@RequestParam
     * List 不管名字一不一样 必须加@RequestParam
     * @return
     */


    @Reference
    private CheckItemService checkItemService;


    /**
     * 添加checkItem对象的方法
     * @param checkItem 传递来的json格式 对象
     * @return 前端接收的结果集
     */
    @RequestMapping("/add")
    public Result addCheckItem(@RequestBody CheckItem checkItem) {

        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){

            return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
        }


    }

    /**
     * 分页查询
     * @param queryPageBean 前端接收的分页条件信息
     * @return 分页对象  包含 总记录数 以及集合
     */
    @RequestMapping("/findpage")
    public PageResult pageCheckItem(@RequestBody QueryPageBean queryPageBean){


        return checkItemService.queryPage(queryPageBean);


    }


    /**
     * 根据id查询 检查项
     * @param id 前端uri传递来的id
     * @return 结果集 用于判断是否成功  并返回查询结果
     */
    @RequestMapping("/findbyid")
    public Result findCheckItemById(Integer id){

        try {
            CheckItem checkItem =  checkItemService.findCheckById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){

            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

    /**
     * 编辑检查项的方法
     * @param checkItem 前端传递来的检查项信息
     * @return  结果集 判断是否成功
     */
    @RequestMapping("/edit")
    public Result updateCheckItem(@RequestBody CheckItem checkItem){

        try {
            checkItemService.editCheck(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){

            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }

    }

    /**
     * 根据id进行删除的操作
     * @param id
     * @return
     */
    @RequestMapping("/deletebyid")
    public Result deleteCheckItem(Integer id) throws DeleteException {

        try {
            checkItemService.deleteCheck(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }catch (DeleteException e){

            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }


    @RequestMapping("/findall")
    public Result findAllCheckItem(){

        try {
            List<CheckItem> list = checkItemService.findAll();
            return Result.success("查询成功",list);
        }catch (DeleteException e){

            return Result.error("查询失败");
        }

    }


}
