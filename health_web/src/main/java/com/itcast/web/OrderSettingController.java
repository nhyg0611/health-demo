package com.itcast.web;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.pojo.OrderSetting;
import com.itcast.pojo.Result;
import com.itcast.service.OrderSettingService;
import com.itcast.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {


    @Reference
    private OrderSettingService orderSettingService;


    /**
     * 设置预约信息的方法
     * @param orderSetting  前端页面传递来的预约信息
     * @return  返回结果集 是否添加成功
     */
    @RequestMapping("/edit")
    public Result editSetting(@RequestBody OrderSetting orderSetting){

        try {
            orderSettingService.editOrderSetting(orderSetting);
            return Result.success("添加成功");
        }catch (Exception e) {
            return Result.error("添加失败");
        }
    }


    /**
     * 根据月份 来刷新当前日历的预约信息
     * @param month 前端页面传递来的 截取后的月份信息
     * @return  结果集 带上 数据库中查询到 满足条件的集合
     */
    @RequestMapping("/findbymonth")
    public Result findByMonth(String month){
        //前端需要的数据格式 每个元素为以下的格式 在不创建实体类的情况下 需要使用map集合对其封装
        //{"date":22,"mouth":10,"number":300,"reservations":300}

        List<Map> list = orderSettingService.findByMonth(month);

        return Result.success("查询成功",list);

    }

    /**
     * 上传的方法
     * @param excelFile 前端页面传递的文件信
     * @return  结果集 判断是否上传成功
     */
    @RequestMapping("/upload")
    public Result uploadExcel(MultipartFile excelFile){
        try {
            //使用工具进行文件的读取操作
            List<String[]> strings = POIUtils.readExcel(excelFile);
            //已经知道 该数组中存在 时间 以及次数  对其进行判断校验

            List<OrderSetting> list = new ArrayList<>();
            //判断上传文件中 是否存在数据
            if (CollectionUtil.isNotEmpty(strings)){
                //对数据进行遍历
                for (String[] string : strings) {
                //因为已知  一定存在两条数据 所以进行校验 是否有两条数据 如果不满足 则继续进行遍历
                    if (string.length!=2){
                        continue;
                    }
                    String dateStr = string[0];

                    String numberStr = string[1];

                    Date orderDate=null;
                    Integer number=null;
                    try {
                        //因为读取的文件为字符串表现形式 需要对其进行解析为所需要的类型
                        orderDate = DateUtil.parse(dateStr,"yyyy/MM/dd");
                        number=Integer.parseInt(numberStr);
                    }catch (Exception e){
                        //如果解析出现异常 则数据不合法 但是还要继续进行解析下面的操作
                        continue;
                    }
                    //将解析成功的数据  进行封装 并且添加到当前的数据库中
                    OrderSetting orderSetting = new OrderSetting(orderDate,number);
                    list.add(orderSetting);
                    //调用添加的方法 进行保存 单线程 35 s 多线程 2s 3000条数据
//                    orderSettingService.addOrderSetting(orderSetting);
                }
            }
            orderSettingService.threadOrderSetting(list);

            return Result.success("上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("上传失败");
        }
    }
}
