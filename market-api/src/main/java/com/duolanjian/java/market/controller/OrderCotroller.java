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

import com.duolanjian.java.market.bean.Cart;
import com.duolanjian.java.market.bean.Commodity;
import com.duolanjian.java.market.bean.Order;
import com.duolanjian.java.market.bean.OrderDetail;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.service.CartService;
import com.duolanjian.java.market.service.CommodityService;
import com.duolanjian.java.market.service.OrderDetailService;
import com.duolanjian.java.market.service.OrderService;
import com.duolanjian.java.market.validation.NeedLogin;
import com.duolanjian.java.market.validation.Validation;

@RestController
public class OrderCotroller {
	@Autowired
	private Validation validation;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CommodityService commodityService;
	
	@NeedLogin
	@RequestMapping(value="/orders", method=RequestMethod.POST)
//  @ResponseBody
  public Object post(User loginUser,@RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
	
		Order order = validation.getObject(body, Order.class, new String[] {"detail","remarks","total","address"});
		order.setUserId(loginUser.getId());
		String details[] = order.getDetail().split(";");
		for(int i = 0 ;i<details.length;i++){
			order.setShopId(Long.valueOf(details[i].substring(0,details[i].indexOf(":"))));
			
			Long id = orderService.insert(order);
			String cartIds[] = details[i].substring(details[i].indexOf(":")+1).split(",");
			for(int j = 0;j<cartIds.length;j++){
				creatOrder_detail(cartIds[j], id);
			}
		}
				
		return result;
  }

	@NeedLogin
	@RequestMapping(value="/orders", method=RequestMethod.GET)
//  @ResponseBody
  public Object get(User loginUser,@RequestParam(defaultValue="0") Long id,
		  @RequestParam(defaultValue="1") int page,
  		@RequestParam(defaultValue="20") int pageSize){
		Map<String,Object> result = new HashMap<String, Object>();
		List<Order> orders = new ArrayList<Order>();
		List<OrderDetail> detail = new ArrayList<OrderDetail>();
		if(id == 0){
			orders = orderService.selectListByPage(loginUser.getId(),page, pageSize);
		}else{
			Order order = orderService.selectOne(id);
			orders.add(order);
			detail = orderDetailService.select(id);
		}
		result.put("order",orders);
		result.put("detail",detail);
		return result;
  }
	
	@NeedLogin
	@RequestMapping(value="/orders", method=RequestMethod.PUT)
//  @ResponseBody
  public Object put(User loginUser,@RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		Order order = validation.getObject(body, Order.class,new String[]{"id","state"});
		Order norder = orderService.selectOne(order.getId());
		norder.setState(order.getState());
		orderService.update(norder);
		return result;
  }
	
	public void creatOrder_detail(String cartId,Long orderId) {
		Cart cart = cartService.selectOne(Long.valueOf(cartId));
		Commodity commodity = commodityService.selectOne(cart.getCommodityId());
		OrderDetail orderDetail = new OrderDetail();
		
		orderDetail.setOrderId(orderId);
		orderDetail.setCommodityId(cart.getCommodityId());
		orderDetail.setName(commodity.getName());
		orderDetail.setNumber(cart.getNumber());
		orderDetail.setPrice(commodity.getPrice());
		orderDetailService.insert(orderDetail);
	}
				
		
        	
        
	
}
