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

import com.duolanjian.java.market.bean.Shop;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.service.ShopService;
import com.duolanjian.java.market.util.JedisUtil;
import com.duolanjian.java.market.util.MD5Util;
import com.duolanjian.java.market.validation.NeedLogin;
import com.duolanjian.java.market.validation.Validation;
import com.duolanjian.java.market.view.ShopView;
import com.duolanjian.java.market.view.UserView;

@RestController
public class ShopController {

	@Autowired
	private Validation validation;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private JedisUtil jedisUtil;
	
	@NeedLogin
	@RequestMapping(value="/shops", method=RequestMethod.POST)
	public  Object post(User loginUser,@RequestBody String body){
		if(loginUser.getRole()!=2) {
			throw new InvalidArgumentException("权限不足！");
		}
		Map<String,Object> result = new HashMap<String, Object>();
		Shop shop = validation.getObject(body, Shop.class, new String[]{"name"});
		shop.setUserId(loginUser.getId());
		long id = shopService.insert(shop);
		result.put("id", id);
		return result;
    }
	
	@NeedLogin
	@RequestMapping(value="/shops", method=RequestMethod.GET)
	public  Object get(User loginUser,
			@RequestParam(defaultValue="0") Long id, 
			@RequestParam(defaultValue="") String name, 
			@RequestParam(defaultValue="false") boolean self,
    		@RequestParam(defaultValue="1") int page,
    		@RequestParam(defaultValue="20") int pageSize){
		List<ShopView> shops=new ArrayList<ShopView>();
		if (self) {
			shops.add(new ShopView(shopService.selectUserId(loginUser.getId())));
		}else {
			if (id!=0) {
				shops.add(new ShopView(shopService.selectOne(id)));
			}
			else {	
				List<Shop> shopList=shopService.selectListName(name, page, pageSize);
				for (Shop shop : shopList) {
					shops.add(new ShopView(shop)); 
				}
			}
		}
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("data", shops);
		return result;
	}
	
	@NeedLogin
	@RequestMapping(value="/shops", method=RequestMethod.PUT)
	public Object put(User loginUser, @RequestBody String body) {
		if(loginUser.getRole()!=2) {
			throw new InvalidArgumentException("权限不足！");
		}
		Map<String,Object> result = new HashMap<String, Object>();
		Shop shop=validation.getObject(body, Shop.class, new String[]{"openSchoolTime","stopSchoolTime","openDormTime","stopDormTime"});
		Shop src=shopService.selectOne(shop.getId());
		if (src == null) {
			throw new InvalidArgumentException("不存在的店铺。");
		}
		if (!loginUser.getId().equals(src.getUserId())) {
			throw new InvalidArgumentException("非法操作！");
		}
		shopService.updata(shop);
		
		return result;
	}
	@NeedLogin
	@RequestMapping(value="/shops", method=RequestMethod.DELETE)
    public Object get(User loginUser, @RequestParam Long id){
		loginUser.checkLevel(3);
        Map<String, Object> result=new HashMap<String, Object>();
        shopService.delete(id);
        return result;
    }
}
