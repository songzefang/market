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
import com.mysql.fabric.xmlrpc.base.Data;

/***
 * 
 * @author 冯梓铭
 *
 */
public class Shop {
	
	private Long id;
	
	@IsString(minLength=4,maxLength=15)
	private String name;
	
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	
	@IsInt(min=0,max=1440)
	private Integer openSchoolTime;
	
	@IsInt(min=0,max=1440)
	private Integer stopSchoolTime;
	
	@IsInt(min=0,max=1440)
	private Integer openDormTime;
	
	@IsInt(min=0,max=1440)
	private Integer stopDormTime;
	
	private Long addressId;
	
	private Long fileId;
	
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.createTime = sdf.parse(createTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException("invalid params.createTime parse error.");
		}
	}

	public Integer getOpenSchoolTime() {
		return openSchoolTime;
	}

	public void setOpenSchoolTime(Integer openSchoolTime) {
		this.openSchoolTime = openSchoolTime;
	}

	public Integer getStopSchoolTime() {
		return stopSchoolTime;
	}

	public void setStopSchoolTime(Integer stopSchoolTime) {
		this.stopSchoolTime = stopSchoolTime;
	}

	public Integer getOpenDormTime() {
		return openDormTime;
	}

	public void setOpenDormTime(Integer openDormTime) {
		this.openDormTime = openDormTime;
	}

	public Integer getStopDormTime() {
		return stopDormTime;
	}

	public void setStopDormTime(Integer stopDormTime) {
		this.stopDormTime = stopDormTime;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void check() {
		if(StringUtil.isEmpty(name)) {
			throw new InvalidArgumentException("店铺名不能为空");
		}
		
		if(StringUtil.isEmpty(addressId.toString())||addressId==0) {
			throw new InvalidArgumentException("地址不能为空");
		}
		
		if(StringUtil.isEmpty(userId.toString())||userId==0) {
			throw new InvalidArgumentException("店主不能为空");
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
