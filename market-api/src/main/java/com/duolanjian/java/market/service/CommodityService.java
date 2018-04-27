package com.duolanjian.java.market.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duolanjian.java.market.bean.Commodity;
import com.duolanjian.java.market.bean.Shop;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.mapper.CommodityMapper;
import com.duolanjian.java.market.util.CommonUtil;
import com.duolanjian.java.market.util.Constant.CommodityState;
import com.duolanjian.java.market.util.DateTool;

/***
 * 
 * @author 冯梓铭
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class CommodityService {
	
	@Autowired
	private CommodityMapper commodityMapper;
	
	public long insert(Commodity commodity) {
		commodity.check();
		commodity.setState(CommodityState.UP);
		CommonUtil.setDefaultValue(commodity);
		commodityMapper.insert(commodity);
		return commodity.getId();
	}
	
	public void delete(long id) {
		commodityMapper.delete(id);
	}
	
	public void updata(Commodity commodity) {
		commodityMapper.update(commodity);
	}
	
	public Commodity selectOne(Long id) {
		return commodityMapper.selectOne(id);
	}
	
	public List<Commodity> selectListName(String name,int page,int pageSize) {
		return commodityMapper.selectListName(name, pageSize, (page-1)*pageSize);
	}
	public List<Commodity> selectListType(int type,int page,int pageSize) {
		return commodityMapper.selectListType(type, pageSize, (page-1)*pageSize);
	}
	public List<Commodity> selectListShopId(long shopId) {
		return commodityMapper.selectListShopId(shopId);
	}
	public List<Commodity> selectAllShopId(long shopId) {
		return commodityMapper.selectAllShopId(shopId);
	}
	public void updateSale(long id) {
		Commodity commodity=commodityMapper.selectOne(id);
		commodityMapper.updateSale((commodity.getSale()+1), id);
	}
	
}
