package com.cxd.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cxd.pojo.User;
import com.cxd.service.UserOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;



@Service
public class UserOperationsServiceImpl implements UserOperationsService {
	@Autowired
	private RedisTemplate redisTemplate;
	Collection<String> colls = new ArrayList<String>();
	@Override
	public void add(User user) {
		ValueOperations<String, User> valueops = redisTemplate.opsForValue();
		valueops.set(user.getSname(), user);
		colls.add(user.getSname());
	}


	@Override
	public User getUser(String key) {
		ValueOperations<String, User> valueops = redisTemplate.opsForValue();
		User user = valueops.get(key);
		return user;
	}


	@Override
	public List<User> getUsers() {
		ValueOperations<String, User> valueops = redisTemplate.opsForValue();
		valueops.multiGet(colls);
		List<User> lists=valueops.multiGet(colls);
		return lists;
	}



	@Override
	public void deldate(String key) {
		ValueOperations<String, User> valueops = redisTemplate.opsForValue();
		RedisOperations<String, User> ro=valueops.getOperations();
		ro.delete(key);
	}



}
