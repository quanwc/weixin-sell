package com.quanwc.weixin.aspect;

import com.quanwc.weixin.constant.CookieConstant;
import com.quanwc.weixin.constant.RedisConstant;
import com.quanwc.weixin.exception.SellerAuthorizeException;
import com.quanwc.weixin.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 卖家端授权AOP切面
 * @author quanwenchao
 * @date 2019/1/21 19:47:25
 */
@Slf4j
@Aspect
@Component
public class SellerAuthorizeAspect {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 授权方法的声明：定义切入点
	 */
	@Pointcut("execution(public * com.quanwc.weixin.controller.Seller*.*(..))"
			+ " && !execution(public * com.quanwc.weixin.controller.SellerUserController.*(..))")
	public void verify() {
	}

	/**
	 * 授权方法的具体实现：在verify()方法之前执行
	 */
	@Before("verify()")
	public void doVerify() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if (cookie == null) {
			log.warn("[登录校验] Cookie中查不到token");
			throw new SellerAuthorizeException();
		}

		// 去redis查询token
		String tokenValue = stringRedisTemplate.opsForValue()
				.get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

		if (tokenValue == null) {
			log.warn("[登录校验] Redis中查不到token");
			throw new SellerAuthorizeException();
		}
	}
}
