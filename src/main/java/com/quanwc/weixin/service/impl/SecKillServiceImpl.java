package com.quanwc.weixin.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.exception.SellException;
import com.quanwc.weixin.service.RedisLockService;
import com.quanwc.weixin.service.SecKillService;
import com.quanwc.weixin.util.KeyUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 秒杀功能的service实现类
 * @author quanwenchao
 * @date 2019/1/24 10:05:53
 */
@Slf4j
@Service
public class SecKillServiceImpl implements SecKillService {

	private static final int TIMEOUT = 10 * 1000; // 超时时间 10s

	@Autowired
	private RedisLockService redisLockService;

	/**
	 * 国庆活动，皮蛋粥特价，限量100000份
	 */
	static Map<String, Integer> productMap;
	static Map<String, Integer> stockMap;
	static Map<String, String> orderMap;

	static {
		/**
		 * 模拟多个表: 商品信息表，库存表，秒杀成功订单表
		 */
		productMap = new HashMap<>();
		stockMap = new HashMap<>();
		orderMap = new HashMap<>();
		productMap.put("123456", 100000); // productId为123456的商品有100000条
		stockMap.put("123456", 100000);
	}

	/**
	 * 查询秒杀活动特价商品的信息
	 * @param productId
	 * @return
	 */
	@Override
	public String querySecKillProductInfo(String productId) {
		return queryMap(productId);
	}

	/**
	 * 模拟不同用户秒杀同一商品的请求
	 * @param productId
	 */
	@Override
	// public synchronized void orderProductMockDiffUser(String productId) {
	public void orderProductMockDiffUser(String productId) {

		// 加锁
		long time = System.currentTimeMillis() + TIMEOUT;
		if (redisLockService.lock(productId, String.valueOf(time))) {
			throw new SellException(ExceptionResultEnum.SEC_KILL_ERROR);
		}

		// 1.查询该商品库存，库存为0则活动结束
		int stockNum = stockMap.get(productId);
		if (stockNum == 0) {
			throw new SellException(100, "活动结束");
		}
		else {
			// 2.下单(模拟不同用户openid不同)
			orderMap.put(KeyUtil.generateUniqueKey(), productId);
			// 3.减库存
			stockNum = stockNum - 1;
			try {
				// 当前线程休眠100毫秒：模拟网络请求的延迟、减库存消耗的时间
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			stockMap.put(productId, stockNum);
		}

		// 解锁
		redisLockService.unlock(productId, String.valueOf(time));

	}

	private String queryMap(String productId) {
		return "国庆活动，皮蛋粥特价，限量份" + productMap.get(productId) + " 还剩："
				+ stockMap.get(productId) + " 份" + " 该商品成功下单用户数目：" + orderMap.size()
				+ " 人";
	}
}
