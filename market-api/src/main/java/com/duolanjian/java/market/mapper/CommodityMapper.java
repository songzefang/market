package com.duolanjian.java.market.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.duolanjian.java.market.bean.Commodity;

/***
 * 
 * @author 冯梓铭
 *
 */
@Component
public interface CommodityMapper extends MapperI<Commodity> {
	
	public List<Commodity> selectListName(@Param("name") String name, @Param("limit") int limit,
			@Param("offset") int offset);

	public List<Commodity> selectListType(@Param("type") int type, @Param("limit") int limit,
			@Param("offset") int offset);

	public List<Commodity> selectListShopId(@Param("shopId") long shopId);

	public List<Commodity> selectAllShopId(@Param("shopId") long shopId);
	
	public void updateSale(@Param("sale") int sale,@Param("id") long id);
}
