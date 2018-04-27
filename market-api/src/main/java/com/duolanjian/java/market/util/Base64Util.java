package com.duolanjian.java.market.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

import javax.imageio.stream.FileImageOutputStream;

import org.springframework.stereotype.Component;

@Component
public class Base64Util {
	//图片转化成base64字符串  
    public static StringBuffer getBase64Sting(String url)  
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        StringBuffer text=new StringBuffer();
        InputStream in = null;  
        byte[] data = new byte[240];  //缓冲区24的倍数
        //读取图片字节数组  
        try {
			in = new FileInputStream(url);  
			int x;
			while ((x=in.read(data))!=-1) {
				//对字节数组Base64编码    
				String str = Base64.getEncoder().encodeToString(data);
				text.append(str);
			}
			in.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        text.trimToSize();
        return text;//返回Base64编码过的字节数组字符串  
    }
    //转换成文件
   	public static String getFile(StringBuffer data,/*int fileType,*/long userId) {
   		//StringBuffer filePath= path(fileType);
   		StringBuffer filePath= new StringBuffer(System.getProperty("user.dir")+"/src/main/webapp/WEB-INF/upload/");
   		//
   		String name=new Date().getTime()+""+userId+".";
   		filePath.append(name);
   		//
   		int start = data.indexOf(":");
   	    int end=data.indexOf("/");
   	    String type=data.substring(++start, end);
   	    if (type.equals("image")) {
   	    	image(data, filePath);
   	    }
   	    int index=data.indexOf(",");
   	    data.delete(0, ++index);
   	    byte[] b=null;
   	   
   		try {
   			String s=data.toString();
   			b=s.getBytes("UTF-8");
   			b=Base64.getDecoder().decode(b);
   			File file=new File(filePath.toString());
   			System.out.println(file.getPath());
   			FileImageOutputStream imageOutput = null;
               try {
                   imageOutput = new FileImageOutputStream(file);
                   imageOutput.write(b, 0, b.length);
                   imageOutput.close();
               } catch (Exception e) {
                   
               }      
   		} catch (Exception e) {
   			
   		}
   			
   		return filePath.toString();
   	}
   	private static StringBuffer path(int type) {
   		StringBuffer path=new StringBuffer();
   		switch (type) {
   		case 1:
   			path.append("E:\\files\\picture\\user\\");
   			break;
   		case 2:
   			path.append("E:\\files\\picture\\shop\\");
   			break;
   		case 3:
   			path.append("E:\\files\\picture\\commodity\\");
   			break;
   		default:
   			break;
   		}
   		return path;
   	}
   	//添加文件后缀名
   	private static void image(StringBuffer data,StringBuffer filePath) {
   		int start = data.indexOf("/");
   	    int end = data.indexOf(";");
   	    String suffix=data.substring(++start, end);
   	    filePath.append(suffix);
   	}
   	public static void main(String[] args) {
		StringBuffer data=new StringBuffer("data:image/jpeg;base64,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");
   		System.out.println(getFile(data, 1));
   		System.out.println(System.getProperty("user.dir") );
	}
   }