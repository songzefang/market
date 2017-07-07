package com.duolanjian.java.market.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.exception.InvalidHttpArgumentException;
import com.duolanjian.java.market.util.StringUtil;
import com.duolanjian.java.market.validation.IsInt;
import com.duolanjian.java.market.validation.IsString;
import com.fasterxml.jackson.annotation.JsonFormat;


public class User {

	private Long id;
	
	@IsString(minLength=11,maxLength=11)
	private String mobile;
	
	@IsString(minLength=1,maxLength=15)
	private String username;
	
	@IsString(minLength=1,maxLength=32)
	private String password;
	
	@IsInt(min=0,max=2)
	private Integer sex;
	
	@IsString(minLength=1,maxLength=15)
	private String qq;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	
	@IsInt(min=0,max=3)
	private Integer role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public void setCreateTime(String createTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.createTime = sdf.parse(createTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException("invalid params.createTime parse error.");
		}
	}
	
	public void check() {
		if(StringUtil.isEmpty(mobile)) {
			throw new InvalidArgumentException("手机号不能为空");
		}
		
		if(StringUtil.isEmpty(password)) {
			throw new InvalidArgumentException("密码不能为空");
		}
	}
	
	public void checkLevel(int level) {
		
		if(level <= this.role) {
			throw new InvalidArgumentException("权限不够");
		}
	}

	public String toString() {
		try {
			return JSON.toJSONString(this);
		} catch (Exception e) {
		}
		return "json parse failed";
	}
}
