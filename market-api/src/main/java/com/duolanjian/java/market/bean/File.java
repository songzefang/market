package com.duolanjian.java.market.bean;

import com.alibaba.fastjson.JSON;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.util.StringUtil;
import com.duolanjian.java.market.validation.IsInt;
import com.duolanjian.java.market.validation.IsString;

/***
 * 
 * @author 冯梓铭
 *
 */
public class File {

	private Long id;
	
	//保存文件的路径
	@IsString(minLength = 0, maxLength = 63)
	private String url;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	
	public void check() {
		if(StringUtil.isEmpty(url)) {
			throw new InvalidArgumentException("url不能为空");
		}
		

	}
	public String toString() {
		try {
			return JSON.toJSONString(this);
		} catch (Exception e) {
		}
		return "json parse failed";
	}
}
