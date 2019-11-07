package com.itcast.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.itcast.mapper.OrderSettingMapper;
import com.itcast.pojo.OrderSetting;
import com.itcast.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingMapper orderSettingMapper;

    /**
     * 编辑 预约信息的方法
     * @param orderSetting  前端传递来的预约信息
     */
    @Override
    public void editOrderSetting(OrderSetting orderSetting) {

        //获得预约的日期
        Date orderDate = orderSetting.getOrderDate();

        //根据预约设置的日期进行判断数据是否存在 如果存在的话 就更新 不存在 就添加

        Integer count =orderSettingMapper.findCountByDate(orderDate);
        if (null!=count&&count!=0){
            orderSettingMapper.editOrderSetting(orderSetting);

        }else {
            orderSettingMapper.addOrderSetting(orderSetting);
        }
    }


    /**
     * 查询当前 月份的预约集合信息
     * @param month 前端传递来的月份信息
     * @return 返回当前 月份的预约集合信息
     */
    @Override
    public List<Map> findByMonth(String month) {
        //得到了月份 可以拼接一个索引 把开始 和结束的月份 进行拼接
        String start = month +"-01";
        String end = month +"-31";
        //然后可以查询到满足该区间的所有OrderSetting对象
        List<OrderSetting> byMonth = orderSettingMapper.findByMonth(start, end);
        //创建一个空的满足要求的集合 用来进行返回
        List<Map> list = new ArrayList<>();
        //可以先对其进行判断 在进行遍历
        if (CollectionUtil.isNotEmpty(byMonth)){
            //对其进行遍历
//            {"date":22,"mouth":10,"number":300,"reservations":300}
            for (OrderSetting orderSetting : byMonth) {
                //根据前端页面所需要的字段信息 集合的添加操作
                Map map = new HashMap();

                map.put("date",orderSetting.getOrderDate().getDate());
                map.put("mouth",orderSetting.getOrderDate().getMonth());
                map.put("number",orderSetting.getNumber());
                map.put("reservations",orderSetting.getReservations());

                list.add(map);
            }
        }
        return list;
    }

    @Override
    public void addOrderSetting(OrderSetting orderSetting) {
        //添加预约信息的方法
        orderSettingMapper.addOrderSetting(orderSetting);
    }

    /**
     * 采用多线程的方式 进行数据的添加
     * @param list  读取所有数据的集合信息
     */
    @Override
    public void threadOrderSetting(List<OrderSetting> list) {

        //创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        //使用工具类 把传递来的集合 进行平分的操作
        List<List<OrderSetting>> threadlist = Lists.partition(list,100);

        //对划分好的集合进行遍历
        for (List<OrderSetting> orderSettings : threadlist) {

            //创建线程 执行任务
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    for (OrderSetting orderSetting : orderSettings) {

                        addOrderSetting(orderSetting);
                    }
                }
            };
            //提交线程任务
            executorService.submit(runnable);
        }
    }


}
