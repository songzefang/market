package com.duolanjian.java.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duolanjian.java.market.bean.Commodity;
import com.duolanjian.java.market.bean.File;
import com.duolanjian.java.market.mapper.FileMapper;
import com.duolanjian.java.market.util.CommonUtil;
import com.duolanjian.java.market.util.Constant.CommodityState;

/***
 * 
 * @author 冯梓铭
 *
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class FileService {

	@Autowired
	private FileMapper fileMapper;
	
	public long insert(File file) {
		file.check();
		CommonUtil.setDefaultValue(file);
		System.out.println(file.getId()+file.toString());
		fileMapper.insert(file);
		System.out.println(file.getId()+file.toString());
		return file.getId();
	}
	
	public void delete(long id) {
		fileMapper.delete(id);
	}
	
	public void updata(File file) {
		fileMapper.update(file);
	}
	
	public File selectOne(Long id) {
		return fileMapper.selectOne(id);
	}
}
