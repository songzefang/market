package com.duolanjian.java.market.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duolanjian.java.market.bean.Deal;
import com.duolanjian.java.market.mapper.DealMapper;
import com.duolanjian.java.market.util.CommonUtil;
import com.duolanjian.java.market.util.Constant.DealState;
import com.duolanjian.java.market.util.DateTool;

/**
 * 
 * @author 宋泽方
 */

@Transactional(rollbackFor = Exception.class)
@Service
public class DealService {

	@Autowired
	private DealMapper dealMapper;

	public void insert(Deal deal) {
		CommonUtil.setDefaultValue(deal);
		String now = DateTool.standardSdf.format(new Date());
		deal.setCreateTime(now);
		deal.setState(DealState.NOPASS);
		dealMapper.insert(deal);
	}

	public List<Deal> selectListByPage(int page, int pageSize) {
		return dealMapper.selectListByPage(pageSize, (page - 1) * pageSize);
	}

	public Deal selectOne(Long id) {
		return dealMapper.selectOne(id);
	}

	public void delete(Long id) {
		dealMapper.delete(id);
	}

	public void update(Deal deal) {
		dealMapper.update(deal);
	}
}
