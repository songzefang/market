package com.duolanjian.java.market.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.duolanjian.java.market.bean.Order;
import com.duolanjian.java.market.bean.OrderDetail;

@Component
public interface OrderMapper extends MapperI<Order>{
	
	List<Order> selectListByPage(@Param("userId")Long userId,@Param("limit") int limit, @Param("offset") int offset);
		
	List<OrderDetail> selectDetail(@Param("id")Long id); 
	
	
}
