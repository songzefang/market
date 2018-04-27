package com.duolanjian.java.market.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.duolanjian.java.market.bean.Evaluate;
import com.duolanjian.java.market.bean.File;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.exception.InvalidArgumentException;
import com.duolanjian.java.market.service.FileService;
import com.duolanjian.java.market.util.Base64Util;
import com.duolanjian.java.market.util.JedisUtil;
import com.duolanjian.java.market.validation.NeedLogin;
import com.duolanjian.java.market.validation.Validation;
import com.duolanjian.java.market.view.EvaluateView;

@RestController
public class FileController {
		
	@Autowired
	private Validation validation;
		
	@Autowired
	private FileService fileService;	
		
	@Autowired
	private JedisUtil jedisUtil;
	
	@Autowired
	private Base64Util base64Util;
	
	@NeedLogin
	@RequestMapping(value="/files", method=RequestMethod.POST)
	public  Object post(User loginUser,@RequestParam String data) {
		Map<String,Object> result = new HashMap<String, Object>();
		StringBuffer sb=new StringBuffer(data);
		String url=Base64Util.getFile(sb, loginUser.getId());
		File file=new File();
		file.setUrl(url);
		long id=fileService.insert(file);
		result.put("id", id);
		return result;
	}
//	@NeedLogin
//	@RequestMapping(value="/files", method=RequestMethod.POST)
//	public  Object post(User loginUser,@RequestParam MultipartFile data,MultipartHttpServletRequest request) throws IOException{
//		Map<String,Object> result = new HashMap<String, Object>();
//		 if(data.isEmpty()){  
//            throw new InvalidArgumentException("文件未上传！");
//         } 
////        System.out.println("文件长度: " + data.getSize());  
////        System.out.println("文件类型: " + data.getContentType());  
////        System.out.println("文件名称: " + data.getName());  
////        System.out.println("文件原名: " + data.getOriginalFilename());  
////        System.out.println("========================================");
//        String name=new Date().getTime()+data.getOriginalFilename();
//        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
//        String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");  
//        //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
//        FileUtils.copyInputStreamToFile(data.getInputStream(), new java.io.File(realPath, name));  
//      
//		File file=new File();
//		file.setUrl("/WEB-INF/upload/"+name);
//		
//		long id=fileService.insert(file);
//		
//		result.put("id", id);
//		
//		return result;
//    }
	
	@RequestMapping(value="/files", method=RequestMethod.GET)
	public  Object get(@RequestParam(defaultValue="0") Long id){
		//
		List<File> files=new ArrayList<File>();
		files.add(fileService.selectOne(id));
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("data", files);
		return result;
	}
	
	
	@NeedLogin
	@RequestMapping(value="/files", method=RequestMethod.DELETE)
    public Object get(User loginUser, @RequestParam Long id){
		loginUser.checkLevel(3);
        Map<String, Object> result=new HashMap<String, Object>();
        fileService.delete(id);
        return result;
    }
}
