package com.quanwc.weixin.constant;

/**
 * redis常量
 * @author quanwenchao
 * @date 2019/1/20 16:33:46
 */
public interface RedisConstant {
	/**
	 * token的前缀
	 */
	String TOKEN_PREFIX = "token_";

	/**
	 * redis过期时间：2小时
	 */
	Integer EXPIRE_TIME = 7200;
}
