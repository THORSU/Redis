package com.cxd.service;

import com.cxd.pojo.User;

/**
 * Created by cxd on 2017/8/27.
 */
public interface UserService {
    public User getUserById(String u_id);
    public User login(User user);
}
