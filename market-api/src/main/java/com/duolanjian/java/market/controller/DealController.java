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

import com.duolanjian.java.market.bean.Deal;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.service.DealService;
import com.duolanjian.java.market.util.Constant.DealState;
import com.duolanjian.java.market.validation.NeedLogin;
import com.duolanjian.java.market.validation.Validation;

@RestController
public class DealController {
	@Autowired
	private Validation validation;

	@Autowired
	private DealService dealService;

	@NeedLogin
	@RequestMapping(value = "/deals", method = RequestMethod.POST)
	// @ResponseBody
	public Object post(User loginUser, @RequestBody String body) { // 新建
		Map<String, Object> result = new HashMap<String, Object>();
		Deal deal = validation.getObject(body, Deal.class, new String[] {"type", "no", "money" });
		System.out.println(loginUser.getId());
		deal.setUserId(loginUser.getId());
		dealService.insert(deal);
		return result;
	}

	@NeedLogin
	@RequestMapping(value = "/deals", method = RequestMethod.GET)
	// @ResponseBody
	public Object get(User loginUser, // 查询
			@RequestParam(defaultValue = "0") Long id,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Deal> deals = new ArrayList<Deal>();
		if (id == 0) {
			deals = dealService.selectListByPage(page, pageSize);
		} else {
			Deal deal = dealService.selectOne(id);
			deals.add(deal);
		}
		result.put("data", deals);
		return result;
	}

	@NeedLogin
	@RequestMapping(value = "/deals", method = RequestMethod.PUT)
	// @ResponseBody
	public Object put(User loginUser, @RequestBody String body) { // 审核
		Map<String, Object> result = new HashMap<String, Object>();
		loginUser.checkLevel(3);
		Deal deal = validation.getObject(body, Deal.class, new String[] { "id" });
		deal.setState(DealState.PASS);
		dealService.update(deal);
		return result;
	}

	@NeedLogin
	@RequestMapping(value = "/deals", method = RequestMethod.DELETE)
	// @ResponseBody
	public Object delete(User loginUser, @RequestParam Long id) { // 删除
		loginUser.checkLevel(3);
		Map<String, Object> result = new HashMap<String, Object>();
		dealService.delete(id);
		return result;
	}

}
