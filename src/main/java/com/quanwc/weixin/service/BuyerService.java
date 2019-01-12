package com.quanwc.weixin.service;

import com.quanwc.weixin.model.dto.OrderMasterDTO;

/**
 * 买家接口层
 * @author quanwenchao
 * @date 2018/12/31 21:44:44
 */
public interface BuyerService {

	/**
	 * 根据用户openid，orderId查询订单
	 * @param openid
	 * @param orderId
	 * @return
	 */
	OrderMasterDTO findOrderOne(String openid, String orderId);

	/**
	 * 根据用户openid，orderId取消订单
	 * @param openid
	 * @param orderId
	 * @return
	 */
	OrderMasterDTO cancelOrder(String openid, String orderId);
}
