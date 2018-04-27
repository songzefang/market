package com.duolanjian.java.market.view;

import java.util.HashMap;

import com.duolanjian.java.market.bean.Commodity;

public class CommodityView extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 3L;
	
	public CommodityView(Commodity commodity) {
		put("id", commodity.getId());
		put("name", commodity.getName());
		put("type", commodity.getType());
		put("state", commodity.getState());
		put("sale", commodity.getSale());
		put("price", commodity.getPrice());
		put("fileId", commodity.getFileId());
		put("detail", commodity.getDetail());
		put("stock", commodity.getStock());
		put("shopId", commodity.getShopId());
	}
}
