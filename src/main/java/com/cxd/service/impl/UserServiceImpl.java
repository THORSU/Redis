package com.cxd.service.impl;

import com.cxd.mapper.UserMapper;
import com.cxd.pojo.User;
import com.cxd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cxd on 2017/8/27.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserById(String u_id) {
        return userMapper.getUserById(u_id);
    }

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }
}
