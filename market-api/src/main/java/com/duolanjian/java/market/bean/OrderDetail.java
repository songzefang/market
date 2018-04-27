package com.duolanjian.java.market.bean;

/**
 * TODO
 * 
 * @author 宋泽方
 *
 */
public class OrderDetail {
	private Long id;
	
	// 商品id
	private Long commodityId;
	
	//商店id
	private Long shopId;
	
	// 订单id
	private Long orderId;
	
	// 商品数量
	private Integer number;
	
	// 商品名称
	private String name;
	
	// 商品单价
	private Double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	

	

}
