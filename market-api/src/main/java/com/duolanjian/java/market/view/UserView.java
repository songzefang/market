package com.duolanjian.java.market.view;

import java.util.HashMap;

import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.util.DateTool;

public class UserView extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public UserView(User user) {
		put("id", user.getId());
		put("mobile", user.getMobile());
		put("username", user.getUsername());
		put("sex", user.getSex());
		put("qq", user.getQq());
		put("role", user.getRole());
		
		put("createTime", DateTool.standardSdf.format(user.getCreateTime()));
	}
	
}
