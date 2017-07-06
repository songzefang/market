package com.duolanjian.java.market.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.service.UserService;
import com.duolanjian.java.market.util.MD5Util;
import com.duolanjian.java.market.util.MergerUtil;
import com.duolanjian.java.market.validation.NeedLogin;
import com.duolanjian.java.market.validation.Validation;
import com.duolanjian.java.market.view.UserView;

@RestController
public class UserController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MD5Util md5Util;
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
//    @ResponseBody
    public Object post(@RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		User user = validation.getObject(body, User.class, new String[]{"mobile","password"});
		user.setPassword(md5Util.convertMD5(user.getPassword()));
		long id = userService.insert(user);
		result.put("id", id);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/users", method=RequestMethod.PUT)
    public Object put(User user, @RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		User user = validation.getObject(body, User.class, new String[]{"id"});
		User src = userService.selectOne(user.getId());
		if(src == null) {
			throw new InvalidArgumentException("不存在的ID。");
		}
		try{
			user = (User) MergerUtil.merger(src, user);
		} catch (Exception e) {
			throw new InvalidArgumentException(e.getMessage());
		}
		user.setPassword(md5Util.convertMD5(user.getPassword()));
		long id = userService.insert(user);
		result.put("id", id);
		return result;
    }

	
	@RequestMapping(value="/users", method=RequestMethod.DELETE)
    public Object get(@RequestParam Long id){
        Map<String, Object> result=new HashMap<String, Object>();
        userService.delete(id);
        return result;
    }
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
    public Object get(@RequestParam String mobile,
    		@RequestParam String password){
        Map<String, Object> result=new HashMap<String, Object>();
        User user = userService.selectByMobile(mobile);
        if(user == null) {
        	throw new InvalidArgumentException("找不到该手机号。");
        }
        if(!user.getPassword().equals(md5Util.convertMD5(password))) {
        	throw new InvalidArgumentException("密码错误。");
        }
        result.put("data", new UserView(user));
        
        return result;
    }
}
