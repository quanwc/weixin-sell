package com.quanwc.weixin.service;

/**
 * 秒杀功能的service接口层
 * @author quanwenchao
 * @date 2019/1/24 10:04:13
 */
public interface SecKillService {

	/**
	 * 查询秒杀活动特价商品的信息
	 *
	 * @param productId
	 * @return
	 */
	String querySecKillProductInfo(String productId);

	/**
	 * 模拟不同用户秒杀同一商品的请求
	 *
	 * @param productId
	 * @return
	 */
	void orderProductMockDiffUser(String productId);
}
