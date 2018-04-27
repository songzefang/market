package com.duolanjian.java.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.duolanjian.java.market.bean.OrderDetail;

public interface OrderDetailMapper extends MapperI<OrderDetail>{
 
	public List<OrderDetail> select(@Param("id")Long id);
}
