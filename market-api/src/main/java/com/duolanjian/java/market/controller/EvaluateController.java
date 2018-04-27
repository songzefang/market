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

import com.duolanjian.java.market.bean.Evaluate;
import com.duolanjian.java.market.bean.Order;
import com.duolanjian.java.market.bean.Shop;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.service.EvaluateService;
import com.duolanjian.java.market.service.OrderService;
import com.duolanjian.java.market.util.JedisUtil;
import com.duolanjian.java.market.validation.NeedLogin;
import com.duolanjian.java.market.validation.Validation;
import com.duolanjian.java.market.view.CommodityView;
import com.duolanjian.java.market.view.EvaluateView;
import com.duolanjian.java.market.view.ShopView;

@RestController
public class EvaluateController {
	@Autowired
	private Validation validation;
	
	@Autowired
	private EvaluateService evaluateService;	
	
	@Autowired
	private OrderService orderService;	
	
	
	@Autowired
	private JedisUtil jedisUtil;
	
	@NeedLogin
	@RequestMapping(value="/evaluates", method=RequestMethod.POST)
	public  Object post(User loginUser,@RequestBody String body){
		Map<String,Object> result = new HashMap<String, Object>();
		Evaluate evaluate = validation.getObject(body, Evaluate.class, new String[]{"content","grade"});
		
		Order order=orderService.selectOne(evaluate.getOrderId());
		System.out.println(order.getUserId()+""+loginUser.getId());
	
		if (!order.getUserId().equals(loginUser.getId())) {
			//验证是否为本人
			throw new InvalidArgumentException("非法操作!");
		}
		long id = evaluateService.insert(evaluate);
		result.put("id", id);
		return result;
    }
	
	
	@RequestMapping(value="/evaluates", method=RequestMethod.GET)
	public  Object get(@RequestParam(defaultValue="0l") Long orderId){
		//
		List<EvaluateView> evaluates=new ArrayList<EvaluateView>();
		evaluates.add(new EvaluateView(evaluateService.selectOrderId(orderId)));
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("data", evaluates);
		return result;
	}
	
	@NeedLogin
	@RequestMapping(value="/evaluates", method=RequestMethod.PUT)
	public Object put(User loginUser, @RequestBody String body) {
		Map<String,Object> result = new HashMap<String, Object>();
		Evaluate evaluate=validation.getObject(body, Evaluate.class, new String[]{"append"});

		Order order=orderService.selectOne(evaluate.getOrderId());
		if (!order.getUserId().equals(loginUser.getId())) {
			//验证是否为本人
			throw new InvalidArgumentException("非法操作!");
		}
		
		Evaluate src=evaluateService.selectOne(evaluate.getId());
		if (src == null) {
			throw new InvalidArgumentException("不存在的评价!");
		}
		
		
		
		evaluateService.updata(evaluate);
		return result;
	}
	@NeedLogin
	@RequestMapping(value="/evaluates", method=RequestMethod.DELETE)
    public Object get(User loginUser, @RequestParam Long id){
		loginUser.checkLevel(3);
        Map<String, Object> result=new HashMap<String, Object>();
        evaluateService.delete(id);
        return result;
    }

}
