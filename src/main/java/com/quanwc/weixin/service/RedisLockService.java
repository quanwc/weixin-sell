package com.quanwc.weixin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * redis的service：redis的分布式锁
 * @author quanwenchao
 * @date 2019/1/23 22:59:30
 */
@Slf4j
@Component
public class RedisLockService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 加锁
	 * @param key 商品id
	 * @param value 当前时间+超时时间
	 * @return
	 */
	public boolean lock(String key, String value) {

		// redis：setnx命令(set if not exists)
		Boolean ifAbsent = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
		if (ifAbsent) {
			return true;
		}

		// currentValue=A 这两个线程的value都是B 其中一个线程拿到锁
		String currentValue = stringRedisTemplate.opsForValue().get(key);
		// 如果锁过期
		if (!StringUtils.isEmpty(currentValue)
				&& Long.parseLong(currentValue) < System.currentTimeMillis()) {
			// 获取上一个锁的时间
			String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, value);
			if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 解锁
	 * @param key 商品id
	 * @param value
	 * @return
	 */
	public void unlock(String key, String value) {
		try {
			String currentValue = stringRedisTemplate.opsForValue().get(key);
			if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
				stringRedisTemplate.opsForValue().getOperations().delete(key);
			}
		}
		catch (Exception e) {
			log.error("[redis分布式锁]解析异常, {}", e);
		}
	}
}