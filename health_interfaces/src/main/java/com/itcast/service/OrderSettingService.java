package com.itcast.service;

import com.itcast.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {

    public void editOrderSetting(OrderSetting orderSetting);

    List<Map> findByMonth(String month);

    void addOrderSetting(OrderSetting orderSetting);

    void threadOrderSetting(List<OrderSetting> list);
}
