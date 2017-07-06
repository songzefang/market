package com.duolanjian.java.market.service;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.mapper.UserMapper;
import com.duolanjian.java.market.util.CommonUtil;
import com.duolanjian.java.market.util.DateTool;

@Transactional(rollbackFor = Exception.class)
@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public long insert(User user) {
		User src = selectByMobile(user.getMobile());
		
		user.check();
		if(src != null) {
			throw new InvalidArgumentException("已存在的手机号");
		}
		
		String now = DateTool.standardSdf.format(new Date());
		CommonUtil.setDefaultValue(user);
		user.setCreateTime(now);
		userMapper.insert(user);
		return user.getId();
	}
	
	public void deleteByMobile(String mobile) {
		userMapper.deleteByMobile(mobile);
	}
	
	public User selectByMobile(String mobile) {
		return userMapper.selectByMobile(mobile);
	}
	
	public User selectOne(long id) {
		return userMapper.selectOne(id);
	}
	
	public void delete (long id) {
		userMapper.delete(id);
	}
	
}
