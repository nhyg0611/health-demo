package com.itcast.web;


import cn.hutool.core.lang.UUID;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.constant.RedisConstant;
import com.itcast.pojo.PageResult;
import com.itcast.pojo.QueryPageBean;
import com.itcast.pojo.Result;
import com.itcast.pojo.Setmeal;
import com.itcast.service.SetmealService;
import com.itcast.utils.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    //因为要使用
    @Autowired
    JedisPool jedisPool;

    /**
     * 上传图片
     * @param imgFile 文件上传特有属性 在dispatcherServlet中写死了
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile){

        try {
            //读取文件进行截取上传
            String originalFilename = imgFile.getOriginalFilename();

            int i = originalFilename.lastIndexOf(".");

            //文件的后缀
            String suffix = originalFilename.substring(i);

            //截取过后的新文件名
            String newfilename = UUID.randomUUID().toString() + suffix;

            //使用工具类进行上传的操作
            QiniuUtil.upload(imgFile.getBytes(),newfilename);
            //把当前点击的图片 添加到 点击到的缓存当中去
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,newfilename);

            return Result.success("上传成功",newfilename);
        }catch (Exception e){
            return Result.error("文件上传失败");
        }
    }

    /**
     * 添加套餐的方法
     * @param setmeal   前端传递来的套餐信息
     * @return  结果集 是否添加成功
     */
    @RequestMapping("/add")
    public Result  addSetmeal(@RequestBody Setmeal setmeal){

        try {
            //首先在数据库中进行 数据的添加
            setmealService.addSetmeal(setmeal);
            //在添加成功的缓存中 保存当前的图片信息
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
            return Result.success("添加成功");
        }catch (Exception e){

            return Result.error("添加失败");
        }

    }

    /**
     * 分页的操作
     * @param queryPageBean 分页信息对象
     * @return  返回分页的总记录数 以及所需要的集合信息
     */
    @RequestMapping("/findpage")
    public PageResult setmealPage(@RequestBody QueryPageBean queryPageBean){

        return setmealService.findPage(queryPageBean);
    }
}

