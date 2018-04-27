package com.duolanjian.java.market.view;

import java.util.HashMap;
import com.duolanjian.java.market.bean.Shop;
import com.duolanjian.java.market.util.DateTool;

public class ShopView extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 2L;
	
	public ShopView(Shop shop) {
		put("id", shop.getId());
		put("name", shop.getName());
		put("createTime", DateTool.standardSdf.format(shop.getCreateTime()));
		put("openSchoolTime", shop.getOpenSchoolTime());
		put("stopSchoolTime", shop.getStopSchoolTime());
		put("openDormTime", shop.getOpenDormTime());
		put("stopDormTime", shop.getStopDormTime());
		put("addressId", shop.getAddressId());
		put("fileId", shop.getFileId());
		put("userId", shop.getUserId());
	}
}
