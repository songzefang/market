package com.duolanjian.java.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.duolanjian.java.market.bean.Address;
import com.duolanjian.java.market.bean.Cart;
import com.duolanjian.java.market.bean.Commodity;

/***
 * 
 * @author 冯梓铭
 *
 */
@Component
public interface AddressMapper extends MapperI<Address>{
	
	public List<Address> selectListShop(@Param("adress") int adress, @Param("limit") int limit,
			@Param("offset") int offset);
	
}
