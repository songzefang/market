package com.duolanjian.java.market.view;

import java.util.HashMap;

import com.duolanjian.java.market.bean.Address;
import com.duolanjian.java.market.util.DateTool;

public class AddressView extends HashMap<String, Object> {

	private static final long serialVersionUID = 8L;

	public AddressView(Address address) {
		put("id", address.getId());
		put("shopId",address.getShopId());
	}
	
}
