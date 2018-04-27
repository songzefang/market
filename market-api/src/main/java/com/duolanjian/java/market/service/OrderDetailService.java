package com.duolanjian.java.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duolanjian.java.market.bean.OrderDetail;
import com.duolanjian.java.market.mapper.OrderDetailMapper;

@Transactional(rollbackFor = Exception.class)
@Service
public class OrderDetailService {
	
	@Autowired
	private OrderDetailMapper orderDetailMapper;
	
	public void insert(OrderDetail orderDetail) {
		orderDetailMapper.insert(orderDetail);
	}
	
	public List<OrderDetail> select(Long id) {
		return orderDetailMapper.select(id);
	}
}
