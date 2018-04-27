package com.duolanjian.java.market.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duolanjian.java.market.bean.Address;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.mapper.AddressMapper;
import com.duolanjian.java.market.mapper.UserMapper;
import com.duolanjian.java.market.util.CommonUtil;
import com.duolanjian.java.market.util.DateTool;

@Transactional(rollbackFor = Exception.class)
@Service
public class AddressService {
	
	@Autowired
	private AddressMapper addressMapper;
	
	public long insert(Address address) {
		
		String now = DateTool.standardSdf.format(new Date());
		CommonUtil.setDefaultValue(address);
		address.setBindTime(now);
		addressMapper.insert(address);
		return address.getId();
	}
	
	
	public List<Address> selectListByPage(int page, int pageSize) {
		return addressMapper.selectListByPage(pageSize, (page-1)*pageSize);
	}
	
	public Address selectOne(long id) {
		return addressMapper.selectOne(id);
	}
	
	public void delete (long id) {
		addressMapper.delete(id);
	}
	
	public void update(Address address) {
		String now = DateTool.standardSdf.format(new Date());
		address.setBindTime(now);
		addressMapper.update(address);
	}
	public List<Address> selectListShop(int adress ,int page, int pageSize) {
		return addressMapper.selectListShop(adress,pageSize, (page-1)*pageSize);
	}
	
}
