package com.cxd.service;

import com.cxd.pojo.User;

import java.util.List;




public interface UserOperationsService {
	void add(User user);
	User getUser(String key);
	List<User> getUsers();
	void deldate(String key);
}
