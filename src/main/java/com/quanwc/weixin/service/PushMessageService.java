package com.quanwc.weixin.service;

import com.quanwc.weixin.model.dto.OrderMasterDTO;

/**
 * 推送消息service接口层
 * @author quanwenchao
 * @date 2019/1/21 21:34:05
 */
public interface PushMessageService {

	/**
	 * 订单状态变更消息
	 * @param orderMasterDTO
	 */
	void orderStatusMessage(OrderMasterDTO orderMasterDTO);
}
