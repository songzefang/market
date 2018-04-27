package com.duolanjian.java.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.duolanjian.java.market.bean.Shop;

/***
 * 
 * @author 冯梓铭
 *
 */
@Component
public interface ShopMapper extends MapperI<Shop> {
	//查询店主店铺
	public Shop selectUserId(@Param("userId")long userId);
	//通过名字查询店铺集合
	public List<Shop> selectListName(@Param("name") String name, @Param("limit") int limit, @Param("offset") int offset);
}
