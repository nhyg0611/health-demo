package com.itcast.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.itcast.mapper.UserMapper;
import com.itcast.pojo.User;
import com.itcast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> selectAll() {
        return mapper.findAll();
    }
}
