package com.quanwc.weixin.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.quanwc.weixin.model.dto.OrderMasterDTO;

/**
 * 支付接口层
 * @author quanwenchao
 * @date 2019/1/8 21:20:53
 */
public interface PayService {

	/**
	 * 发起支付
	 * @param orderMasterDTO 订单信息
	 */
	PayResponse create(OrderMasterDTO orderMasterDTO);

	/**
	 * 微信异步通知
	 * @param asyNotifyData
	 */
	PayResponse asyncNotify(String asyNotifyData);

	/**
	 * 微信退款
	 * @param orderMasterDTO
	 * @return
	 */
	RefundResponse refund(OrderMasterDTO orderMasterDTO);
}
