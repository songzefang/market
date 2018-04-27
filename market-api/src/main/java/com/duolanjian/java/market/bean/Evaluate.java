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

/***
 * 
 * @author 冯梓铭
 *
 */
public class Evaluate {

	private Long id;
	
	//评价内容
	@IsString(minLength = 0, maxLength = 255)
	private String content;

	//创建时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	
	//追加评价内容
	@IsString(minLength = 0, maxLength = 255)
	private String append;
	
	//追加时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date appendTime;
	
	//本次消费的等级评价的星级（1~5）
	@IsInt(min = 1, max = 5)
	private Integer grade;
	
	//对本次消费（订单）的pkid
	private Long orderId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAppend() {
		return append;
	}

	public void setAppend(String append) {
		this.append = append;
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

	public Date getAppendTime() {
		return appendTime;
	}

	public void setAppendTime(String appendTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.appendTime = sdf.parse(appendTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException("invalid params.createTime parse error.");
		}
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public void check() {
		if(StringUtil.isEmpty(grade.toString())||grade==0) {
			throw new InvalidArgumentException("评价不能为空");
		}
		
		if(StringUtil.isEmpty(orderId.toString())||orderId==0) {
			throw new InvalidArgumentException("订单的id不能为空");
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
