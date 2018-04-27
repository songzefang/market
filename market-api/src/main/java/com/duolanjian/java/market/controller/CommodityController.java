package com.duolanjian.java.market.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duolanjian.java.market.bean.Commodity;
import com.duolanjian.java.market.bean.Shop;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.service.CommodityService;
import com.duolanjian.java.market.service.ShopService;
import com.duolanjian.java.market.util.JedisUtil;
import com.duolanjian.java.market.util.MD5Util;
import com.duolanjian.java.market.validation.NeedLogin;
import com.duolanjian.java.market.validation.Validation;
import com.duolanjian.java.market.view.CommodityView;
import com.duolanjian.java.market.view.ShopView;
import com.duolanjian.java.market.view.UserView;

@RestController
public class CommodityController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private CommodityService commodityService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private JedisUtil jedisUtil;
	@NeedLogin
	@RequestMapping(value="/commoditys", method=RequestMethod.POST)
	public  Object post(User loginUser,@RequestBody String body){
		if(loginUser.getRole()!=2) {
			throw new InvalidArgumentException("权限不足！");
		}
		
		Map<String,Object> result = new HashMap<String, Object>();
		Commodity commodity = validation.getObject(body, Commodity.class, new String[]{"name","type","detail","stock"});
		Shop shop=shopService.selectUserId(loginUser.getId());
		System.out.println(shop.getId()+ ""+commodity.getShopId());
		if (shop.getId()!=commodity.getShopId()) {
			throw new InvalidArgumentException("非法操作！");
		}
		long id = commodityService.insert(commodity);
		result.put("id", id);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/commoditys", method=RequestMethod.GET)
	public  Object get(User loginUser,
			@RequestParam(defaultValue="0") Long id,
			@RequestParam(defaultValue="0") Long shopId,
			@RequestParam(defaultValue="0") int type, 
			@RequestParam(defaultValue="") String name, 
			@RequestParam(defaultValue="false") boolean self,
    		@RequestParam(defaultValue="1") int page,
    		@RequestParam(defaultValue="20") int pageSize){
		List<CommodityView> commoditys=new ArrayList<CommodityView>();
		if (self) {
			Shop shop=shopService.selectUserId(loginUser.getId());
			
			List<Commodity> commodityList = commodityService.selectAllShopId(shop.getId());
			for(Commodity commodity : commodityList) {
				commoditys.add(new CommodityView(commodity));
			}
			}else {
				if (id!=0) {
					commoditys.add(new CommodityView(commodityService.selectOne(id)));
				}else if (!name.equals("")) {
					List<Commodity> commodityList = commodityService.selectListName(name, page, pageSize);
					for(Commodity commodity : commodityList) {
						commoditys.add(new CommodityView(commodity));
					}
				}else if (type!=0) {
					List<Commodity> commodityList = commodityService.selectListType(type, page, pageSize);
					for(Commodity commodity : commodityList) {
						commoditys.add(new CommodityView(commodity));
					}
				}else {
					List<Commodity> commodityList = commodityService.selectListShopId(shopId);
					for(Commodity commodity : commodityList) {
						commoditys.add(new CommodityView(commodity));
					}
				}
			
			}
		
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("data", commoditys);
		return result;
	}
	
	@NeedLogin
	@RequestMapping(value="/commoditys", method=RequestMethod.PUT)
	public Object put(User loginUser, @RequestBody String body) {
		if(loginUser.getRole()!=2) {
			throw new InvalidArgumentException("权限不足！");
		}
		Map<String,Object> result = new HashMap<String, Object>();
		Commodity commodity = validation.getObject(body, Commodity.class, new String[]{"state","detail","stock"});
		Shop shop=shopService.selectUserId(loginUser.getId());
		Commodity src = commodityService.selectOne(commodity.getId());
		if (src == null) {
			throw new InvalidArgumentException("不存在的商品!");
		}
		if (shop.getId()!=src.getShopId()) {
			throw new InvalidArgumentException("非法操作！");
		}
		commodityService.updata(commodity);
		
		return result;
	}
	@NeedLogin
	@RequestMapping(value="/commoditys", method=RequestMethod.DELETE)
    public Object get(User loginUser, @RequestParam Long id){
		loginUser.checkLevel(3);
        Map<String, Object> result=new HashMap<String, Object>();
        commodityService.delete(id);
        return result;
    }
}
