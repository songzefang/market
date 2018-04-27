package com.duolanjian.java.market.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.duolanjian.java.market.bean.Evaluate;

/***
 * 
 * @author 冯梓铭
 *
 */
@Component
public interface EvaluateMapper extends MapperI<Evaluate>{

	public Evaluate selectOrderId(@Param("orderId")long orderId);
}
