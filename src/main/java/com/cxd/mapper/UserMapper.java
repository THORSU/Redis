package com.cxd.mapper;

import com.cxd.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * userinfo(个人信息dao层)
 * @author hpf
 *
 */
public interface UserMapper {


    public User getUserById(String u_id);
    public User login(User user);
    /**
     * 功能说明 :根据id查找
     * @param u_id
     * @return
     */

}
