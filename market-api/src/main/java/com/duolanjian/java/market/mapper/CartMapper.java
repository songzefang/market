package com.duolanjian.java.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.duolanjian.java.market.bean.Cart;

@Component
public interface CartMapper extends MapperI<Cart>{
	

	List<Cart> selectAll(@Param("id") Long id);
	
	List<Long> selectShopId(@Param("id") Long id);
	
}
