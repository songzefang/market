package com.duolanjian.java.market.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duolanjian.java.market.bean.Order;
import com.duolanjian.java.market.bean.OrderDetail;
import com.duolanjian.java.market.mapper.OrderMapper;
import com.duolanjian.java.market.util.CommonUtil;
import com.duolanjian.java.market.util.Constant.OrderState;
import com.duolanjian.java.market.util.DateTool;
import com.duolanjian.java.market.util.MD5Util;


/**
 * 
 * @author 宋泽方
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private MD5Util md5Util;

	public Long insert(Order order) {
		String now = DateTool.standardSdf.format(new Date());
		CommonUtil.setDefaultValue(order);
		order.setCreateTime(now);	
		order.setState(OrderState.CREATE);
		orderMapper.insert(order);
		return order.getId();
	}
	
	
	public void update(Order order){
		String now = DateTool.standardSdf.format(new Date());
		if(order.getState()==OrderState.CONFIRM){
			order.setConfirmTime(now);
		}else if(order.getState()==OrderState.DELIVERY){
			order.setDeliveryTime(now);
		}else if(order.getState()==OrderState.ORDER){
			order.setOrderTime(now);
		}else if(order.getState()==OrderState.REFUND){
			order.setRefundTime(now);
		}else if(order.getState()==OrderState.END){
			order.setEndTime(now);
		}
		orderMapper.update(order);
	}
	
	public List<Order> selectListByPage(Long userId,int page, int pageSize) {
		return orderMapper.selectListByPage(userId,pageSize, (page-1)*pageSize);
	}
	
	public Order selectOne(Long id) {
		return orderMapper.selectOne(id);
		
	}

	public List<OrderDetail> selectDetail(Long id){
		return orderMapper.selectDetail(id);
	}

}
