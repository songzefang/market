package com.duolanjian.java.market.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.duolanjian.java.market.exception.InvalidHttpArgumentException;
import com.duolanjian.java.market.validation.IsInt;
import com.fasterxml.jackson.annotation.JsonFormat;

/***
 * 
 * @author 冯梓铭
 *
 */
public class Address {
	
	private Long id;
	
	//学校地址
	private Integer adress;
	
	//寝室号
	private Integer drom;
	
	//经纬度
	private String xy;
	
	//1买家，2店铺
	@IsInt(min=1,max=2)
	private Integer type;
	
	//绑定用户
	private Long userId;
	
	//绑定店铺
	private Long shopId;
	
	//绑定时间
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date bindTime;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAdress() {
		return adress;
	}

	public void setAdress(Integer adress) {
		this.adress = adress;
	}

	public Integer getDrom() {
		return drom;
	}

	public void setDrom(Integer drom) {
		this.drom = drom;
	}

	public String getXy() {
		return xy;
	}

	public void setXy(String xy) {
		this.xy = xy;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(String bindTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.bindTime = sdf.parse(bindTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException("invalid params.createTime parse error.");
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
