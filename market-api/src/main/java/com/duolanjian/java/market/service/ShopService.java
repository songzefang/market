package com.duolanjian.java.market.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duolanjian.java.market.bean.Shop;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.mapper.ShopMapper;
import com.duolanjian.java.market.util.CommonUtil;
import com.duolanjian.java.market.util.DateTool;

/***
 * 
 * @author 冯梓铭
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ShopService {
	
	@Autowired
	private ShopMapper shopMapper;
	
	public long insert(Shop shop) {
		Shop src=shopMapper.selectUserId(shop.getUserId());
		
		shop.check();
		if (src!=null) {
			throw new InvalidArgumentException("用户已拥有店铺");
		}
		String now = DateTool.standardSdf.format(new Date());
		shop.setCreateTime(now);
		CommonUtil.setDefaultValue(shop);
		shopMapper.insert(shop);
		return shop.getId();
	}
	
	public void delete(long id) {
		shopMapper.delete(id);
	}
	
	public void updata(Shop shop) {
		shopMapper.update(shop);
	}
	
	public Shop selectOne(Long id) {
		return shopMapper.selectOne(id);
	}
	
	public Shop selectUserId(Long userId) {
		return shopMapper.selectUserId(userId);
	}
	
	public List<Shop> selectListName(String name,int page,int pageSize) {
		return shopMapper.selectListName(name, pageSize, (page-1)*pageSize);
	}
	
}
