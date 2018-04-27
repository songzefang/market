package com.duolanjian.java.market.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duolanjian.java.market.bean.Evaluate;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.mapper.EvaluateMapper;
import com.duolanjian.java.market.util.CommonUtil;
import com.duolanjian.java.market.util.DateTool;

/***
 * 
 * @author 冯梓铭
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class EvaluateService {
	
	@Autowired
	private EvaluateMapper evaluateMapper;
	
	public long insert(Evaluate evaluate) {
		Evaluate src=evaluateMapper.selectOrderId(evaluate.getOrderId());
	
		evaluate.check();
		if (src!=null) {
			throw new InvalidArgumentException("评论已存在！！");
		}
		String now = DateTool.standardSdf.format(new Date());
		evaluate.setCreateTime(now);
		CommonUtil.setDefaultValue(evaluate);
		evaluateMapper.insert(evaluate);
		return evaluate.getId();
	}
	
	public void delete(long id) {
		evaluateMapper.delete(id);
	}
	
	public void updata(Evaluate evaluate) {
		String now = DateTool.standardSdf.format(new Date());
		evaluate.setAppendTime(now);
		evaluateMapper.update(evaluate);
	}
	
	public Evaluate selectOne(Long id) {
		return evaluateMapper.selectOne(id);
	}
	
	public Evaluate selectOrderId(Long orderId) {
		return evaluateMapper.selectOrderId(orderId);
	}
}
