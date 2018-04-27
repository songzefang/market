
package com.duolanjian.java.market.bean;

import com.alibaba.fastjson.JSON;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.util.StringUtil;
import com.duolanjian.java.market.validation.IsInt;
import com.duolanjian.java.market.validation.IsString;

/***
 * 
 * @author 冯梓铭
 *
 */
public class Commodity {

	private Long id;
	
	//商品名
	@IsString(minLength=1,maxLength=15)
	private String name;

	//商品类型
	@IsInt(min=1,max=8)
	private Integer type;

	//状态（1上架，2下架）
	@IsInt(min=1,max=3)
	private Integer state;
	
	//商品销量
	@IsInt(min=0,max=999999)
	private Integer sale;

	//商品单价
	private Double price;

	//商品图片
	private Long fileId;

	//商品简介
	@IsString(minLength=0,maxLength=63)
	private String detail;

	//库存
	@IsInt(min=0,max=999999)
	private Integer stock;

	//代表是那个店铺
	private Long shopId;

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

	public Integer getSale() {
		return sale;
	}

	public void setSale(Integer sale) {
		this.sale = sale;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
	public void check() {
		if(StringUtil.isEmpty(name)) {
			throw new InvalidArgumentException("商品名不能为空");
		}
		
		if(StringUtil.isEmpty(type.toString())||type==0) {
			throw new InvalidArgumentException("商品类型不能为空");
		}
		
		if(StringUtil.isEmpty(price.toString())||price==0.0) {
			throw new InvalidArgumentException("商品价钱不能为空");
		}
		
		if(StringUtil.isEmpty(stock.toString())||stock==0) {
			throw new InvalidArgumentException("库存不能为空");
		}
		
		if(StringUtil.isEmpty(shopId.toString())||shopId==0) {
			throw new InvalidArgumentException("店铺不能为空");
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
