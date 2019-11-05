package com.itcast.jobs;

import com.itcast.constant.RedisConstant;
import com.itcast.utils.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class ClearImages {


    @Autowired
    protected JedisPool jedisPool;

    //定时清理的方法
    public void run(){

        //数据多的key放前面
        Set<String> needDeleteImg = jedisPool.getResource().
                sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        for (String img : needDeleteImg) {
            //删除七牛
            QiniuUtil.delete(img);
            //删除redis里面，避免下次再匹配到
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,img);
        }
    }
}
