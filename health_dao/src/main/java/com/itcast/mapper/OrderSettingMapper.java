package com.itcast.mapper;

import com.itcast.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderSettingMapper {

    public void editOrderSetting(OrderSetting orderSetting);

    public void addOrderSetting(OrderSetting orderSetting);

    public Integer findCountByDate(@Param("orderDate")Date orderDate);

    public List<OrderSetting>   findByMonth(@Param("start")String start,@Param("end") String end);
}
