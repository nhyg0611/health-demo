package com.itcast.jobs;

import com.itcast.constant.RedisConstant;
import com.itcast.utils.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Set;

@Component
public class ClearImages {


    @Autowired
    protected JedisPool jedisPool;

    //定时清理的方法

    public void run(){
        System.out.println("测试。。。。");

        //数据多的key放前面
        //返回 不包含 后面集合之外的数据
        //不明确时 使用ctrl +q来看注释
        Set<String> needDeleteImg = jedisPool.getResource().
                sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        //进行遍历 将没有上传的图片信息进行删除
        for (String img : needDeleteImg) {
            //删除七牛
            QiniuUtil.delete(img);
            //删除redis里面，避免下次再匹配到
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,img);
        }
    }

   /* public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationContext-jobs.xml");
    }*/
}
