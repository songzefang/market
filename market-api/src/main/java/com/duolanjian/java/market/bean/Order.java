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
public class Order {
	private Long id;

	// 用户id
	private Long userId;

	// 店铺id
	private Long shopId;

	// 订单状态（1 已下单，2 已支付，3 派送中，4确认收货 ，5.取消，6.退款）
	@IsInt(min = 1, max = 6)
	private Integer state; 

	// 收货地址
	private String address;

	// 订单总价
	private Double total;
	
	//附属表信息
	private String detail;
	
	//备注
	private String remarks;
	
	//订单创建时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime; 

	// 订单支付时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date orderTime; 

	// 订单派送时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date deliveryTime; 

	// 订单确认时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date confirmTime;
	
	// 订单申请退款时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date refundTime;

	// 订单退款结束时间
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;

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

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
					"invalid params.stopTime parse error.");
		}
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.orderTime = sdf.parse(orderTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException(
					"invalid params.stopTime parse error.");
		}
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.deliveryTime = sdf.parse(deliveryTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException(
					"invalid params.stopTime parse error.");
		}
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.confirmTime = sdf.parse(confirmTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException(
					"invalid params.stopTime parse error.");
		}
	}
	
	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.refundTime = sdf.parse(refundTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException(
					"invalid params.stopTime parse error.");
		}
	}
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			this.endTime = sdf.parse(endTime);
		} catch (ParseException e) {
			throw new InvalidHttpArgumentException(
					"invalid params.stopTime parse error.");
		}
	}

}
