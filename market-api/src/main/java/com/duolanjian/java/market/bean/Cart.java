package com.duolanjian.java.market.bean;

/**
 * TODO
 * 
 * @author 宋泽方
 *
 */
public class Cart {
	
	private Long id;
	
	//用户id
	private Long userId;
	
	//商品id
	private Long commodityId;
	
	//商店id
	private Long shopId;
	
	//数量
	private Integer number;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Long getShop_id() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	

}
