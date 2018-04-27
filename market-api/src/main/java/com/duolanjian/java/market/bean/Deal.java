package com.duolanjian.java.market.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.duolanjian.java.market.exception.InvalidHttpArgumentException;
import com.duolanjian.java.market.validation.IsInt;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * TODO
 * 
 * @author 宋泽方
 *
 */
public class Deal {

	private Long id;
	
	//用户id
	private Long userId;
	
	//交易类型（1.充值 2.提现 3.普通交易）
	@IsInt(min = 1,max = 3)
	private Integer type;
	
	//交易流水号
	private String no;
	
	//交易金额
	private Double money;
	
	//交易状态（1.未审核 2.审核通过）
	@IsInt(min = 1,max = 2)
	private Integer state;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.createTime = sdf.parse(createTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException(
					"invalid params.createTime parse error.");
		}
	}


}
