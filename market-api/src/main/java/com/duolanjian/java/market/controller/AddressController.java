package com.duolanjian.java.market.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.duolanjian.java.market.bean.Address;
import com.duolanjian.java.market.bean.Cart;
import com.duolanjian.java.market.bean.Commodity;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.exception.NeedAuthorizationException;
import com.duolanjian.java.market.service.AddressService;
import com.duolanjian.java.market.service.CartService;
import com.duolanjian.java.market.service.ShopService;
import com.duolanjian.java.market.service.UserService;
import com.duolanjian.java.market.util.JedisUtil;
import com.duolanjian.java.market.util.MD5Util;
import com.duolanjian.java.market.util.MergerUtil;
import com.duolanjian.java.market.util.AdressInfo;
import com.duolanjian.java.market.util.Constant.RedisNameSpace;
import com.duolanjian.java.market.validation.NeedLogin;
import com.duolanjian.java.market.validation.Validation;
import com.duolanjian.java.market.view.AddressView;
import com.duolanjian.java.market.view.CommodityView;
import com.duolanjian.java.market.view.UserView;

@RestController
public class AddressController {
	@Autowired
	private Validation validation;
	
	@Autowired
	private  AddressService addressService;
	
	@Autowired
	private  ShopService shopService ;
	
	
	@Autowired
	private JedisUtil jedisUtil;
	
	
	@NeedLogin
	@RequestMapping(value="/address", method=RequestMethod.PUT)
    public Object put(User loginUser, @RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		
		Address address = validation.getObject(body, Address.class, new String[]{"type"});
		Address src= addressService.selectOne(address.getId());
		if(src == null) {
			address.setUserId(loginUser.getId());
			if (address.getType()==2) {
				String xy=AdressInfo.getXy(address.getAdress());
				address.setXy(xy);
				long shopId=shopService.selectUserId(loginUser.getId()).getId();
				address.setShopId(shopId);
			}
			long id=addressService.insert(address);
			result.put("id", id);
			result.put("adress", address.getAdress());
		}
		
		addressService.update(address);
		if (src.getType()==1) {
			int index=AdressInfo.distance(address.getXy());
	        result.put("index", index);
		}
		return result;
    }

	@NeedLogin
	@RequestMapping(value="/address", method=RequestMethod.DELETE)
    public Object delete(User loginUser, @RequestParam Long id){
		loginUser.checkLevel(3);
        Map<String, Object> result=new HashMap<String, Object>();
        addressService.delete(id);
        return result;
    }
	@NeedLogin
	@RequestMapping(value="/address", method=RequestMethod.GET)
    public Object get(User loginUser, @RequestParam String xy,
    		@RequestParam(defaultValue="1") int page,
    		@RequestParam(defaultValue="20") int pageSize){
		loginUser.checkLevel(3);
		List<AddressView> addresses=new ArrayList<AddressView>();
		Map<String, Object> result=new HashMap<String, Object>();
        int adress=AdressInfo.distance(xy);
        List<Address>  addressList= addressService.selectListShop(adress, page, pageSize);
       
		for(Address address : addressList) {
			addresses.add(new AddressView(address));
		}
		result.put("data", addresses);
        return result;
    }

}
