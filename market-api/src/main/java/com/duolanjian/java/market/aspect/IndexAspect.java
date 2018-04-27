package com.duolanjian.java.market.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.duolanjian.java.market.bean.User;
import com.duolanjian.java.market.exception.ExceptionHandlerMap;
import com.duolanjian.java.market.exception.NeedAuthorizationException;
import com.duolanjian.java.market.util.Constant.OpCode;
import com.duolanjian.java.market.util.Constant.RedisNameSpace;
import com.duolanjian.java.market.util.JedisUtil;
import com.duolanjian.java.market.validation.NeedLogin;


//@Component
@Aspect
public class IndexAspect {

	@Autowired
	private ExceptionHandlerMap exceptionHandlerMap;
	
	@Autowired
	private JedisUtil jedisUtil;

	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
	@Pointcut("execution(* com.duolanjian.java.market.controller..*(..))")
	public void aspect(){
	}
	
	public IndexAspect(){
		System.out.println("aspect init...");
	}
	
	/*
	 * 配置前置通知,使用在方法aspect()上注册的切入点
	 * 同时接受JoinPoint切入点对象,可以没有该参数
	 */
	@Around("aspect()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object response = null;
        
        Object[] args = pjp.getArgs();
        Object[] argsModified = new Object[args.length];

        MethodSignature signature = (MethodSignature) pjp.getSignature(); 
        Method method = signature.getMethod();
        Class<?> clazz = method.getDeclaringClass();

        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            String[] paths = requestMapping.value();
            String pathString = "";
            for (String path: paths) {
                pathString += path + ",";
            }
            if ("".equals(pathString)) {
                pathString = "unknowPath";
            } else {
                pathString = pathString.substring(0, pathString.length() - 1);
            }

            RequestMethod[] httpMethods = requestMapping.method();
            String methodString = "";
            for (RequestMethod httpMethod: httpMethods) {
                methodString += httpMethod.name() + ",";
            }

            if ("".equals(methodString)) {
                methodString = "unknowMethod";
            } else {
                methodString = methodString.substring(0, methodString.length() - 1);
            }
        }

        try {
        if (clazz.isAnnotationPresent(NeedLogin.class) || method.isAnnotationPresent(NeedLogin.class)) {
            ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = sra.getRequest();

            User loginInfo = null;
            String ticket = request.getHeader("ticket");

            String sourceIp = request.getHeader("Request-Ip");

            if (sourceIp == null || "".equals(sourceIp)) {
                String forwardedIpString = request.getHeader("X-Forwarded-For");
                if (forwardedIpString != null) {
                    String[] forwardedIps = forwardedIpString.split(",");
                    if (forwardedIps.length > 0 && !"".equals(forwardedIps[0].trim())) {
                        sourceIp = forwardedIps[0].trim();
                    }
                }
            }

            if (sourceIp == null || "".equals(sourceIp)) {
                sourceIp = request.getHeader("X-Real-IP");
            }
            if (sourceIp == null || "".equals(sourceIp)) {
                sourceIp = request.getRemoteAddr();
            }
            if (ticket != null && !"".equals(ticket)) {
                try {
                	System.out.println("key: " + RedisNameSpace.LOGIN + ticket);
                    loginInfo = JSON.parseObject(jedisUtil.get(RedisNameSpace.LOGIN + ticket), User.class);
                } catch(Exception e) {
                    throw new NeedAuthorizationException(RedisNameSpace.LOGIN_URL);
                }
            }

            if (loginInfo == null) {
            	 throw new NeedAuthorizationException("ticket过期，请重新登录");
            }else {
            	//刷新时间
            	jedisUtil.set(RedisNameSpace.LOGIN + ticket, JSON.toJSONString(loginInfo), RedisNameSpace.LOGIN_TIME);
            }

            int i = 0;
            for (Object o: args) {
                if (o instanceof User) {
                    argsModified[i++] = loginInfo;
                } else {
                    argsModified[i++] =  o;
                }
            }
        } else {
            argsModified = args;
        }
		
			response = pjp.proceed(argsModified);
			Map<String, Object> castResponse;
			if (response == null) {
				response = new HashMap<String, Object>();
			}

			if (response instanceof Map) {
				castResponse = (Map<String, Object>) response;
				castResponse
						.put("cost",
								(double) (System.currentTimeMillis() - startTime) / 1000);
				castResponse.put("code", OpCode.SUCCESS);
			}
		} catch (Exception e) {
			Method methodE = exceptionHandlerMap.getMethod(e.getClass());
			System.out.println("method:" + methodE.getName());
			if (methodE != null) {
				Object errorResponse = methodE.invoke(
						exceptionHandlerMap.getExceptionHandler(methodE), e);
				System.out.println("exceptionHandlerMap.getExceptionHandler(methodE):" + exceptionHandlerMap.getExceptionHandler(methodE));
				return errorResponse;
			}
		}

        return response;
	}
	
}
