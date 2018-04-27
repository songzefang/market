package com.duolanjian.java.market.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duolanjian.java.market.bean.Cart;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.service.CartService;
import com.duolanjian.java.market.validation.NeedLogin;
import com.duolanjian.java.market.validation.Validation;

@RestController
public class CartController {
	@Autowired
	private Validation validation;

	@Autowired
	private CartService cartService;

	@NeedLogin
	@RequestMapping(value = "/carts", method = RequestMethod.POST)
	// @ResponseBody
	public Object post(User loginUser, @RequestBody String body) {
		Map<String, Object> result = new HashMap<String, Object>();
		Cart cart = validation.getObject(body, Cart.class, new String[] {"commodityId",
				"shopId" });
		cart.setUserId(loginUser.getId());
		cart.setNumber(1);
		cartService.insert(cart);
		return result;
	}

	@NeedLogin
	@RequestMapping(value = "/carts", method = RequestMethod.DELETE)
	// @ResponseBody
	public Object delete(User loginUser, @RequestParam Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		cartService.delete(id);
		return result;
	}

	@NeedLogin
	@RequestMapping(value = "/carts", method = RequestMethod.PUT)
	// @ResponseBody
	public Object put(User loginUser, @RequestBody String body) {
		Map<String, Object> result = new HashMap<String, Object>();
		Cart cart = validation.getObject(body, Cart.class, new String[] {"id",
				"number" });
		cartService.update(cart);
		return result;
	}

	@NeedLogin
	@RequestMapping(value = "/carts", method = RequestMethod.GET)
	// @ResponseBody
	public Object get(User loginUser, @RequestParam(defaultValue="0")Long id ) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(id==0){
			List<Long> shopIds = cartService.selectShopId(loginUser.getId());
			List<Cart> carts = cartService.selectAll(loginUser.getId());
			StringBuffer data = new StringBuffer();
			if(carts.size() == 0){
				return result;
			}else{
				int i,j=0;
				Long key;
				for(i=0;i<shopIds.size();i++){
					StringBuffer value = new StringBuffer();
					key=shopIds.get(i);
					for(j=0;j<carts.size();j++){
						if(carts.get(j).getShop_id()==key){
							value.append(carts.get(j).getId()+",");
						}
					}
					value.deleteCharAt(value.length()-1);
					data.append(key+":");
					data.append(value+";");	
				}
				data.deleteCharAt(data.length()-1);	
			}
			result.put("data", data);
		}else{
			Cart cart = cartService.selectOne(id);
			result.put("data", cart);
		}
		return result;
	}

}
