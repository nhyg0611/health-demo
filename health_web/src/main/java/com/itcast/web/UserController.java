package com.itcast.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itcast.pojo.User;
import com.itcast.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("selectAll")
    public List<User>  selectAll(){

        return userService.selectAll();
    }
}
