package com.quanwc.weixin.service.impl;

import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.exception.SellException;
import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.service.BuyerService;
import com.quanwc.weixin.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 买家实现类Impl
 * @author quanwenchao
 * @date 2018/12/31 21:48:22
 */
@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	OrderMasterService orderMasterService;

	@Override
	public OrderMasterDTO findOrderOne(String openid, String orderId) {
		return checkOrderOwner(openid, orderId);
	}

	@Override
	public OrderMasterDTO cancelOrder(String openid, String orderId) {
		OrderMasterDTO orderMasterDTO = checkOrderOwner(openid, orderId);
		if (null == orderMasterDTO) {
			log.error("[取消订单]该订单不存在, orderId={}", orderId);
			throw new SellException(ExceptionResultEnum.PRODUCT_NOT_EXIST);
		}
		return orderMasterService.cancel(orderMasterDTO);
	}

	/**
	 * 校验orderId对应的订单是否属于openid的微信用户
	 * @param openid
	 * @param orderId
	 * @return
	 */
	private OrderMasterDTO checkOrderOwner(String openid, String orderId) {
		OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
		if (orderMasterDTO == null) {
			return null;
		}
		// 判断是否是自己的订单
		if (!orderMasterDTO.getBuyerOpenid().equals(openid)) {
			log.error("[查询订单]订单的openid不一致, openid={}, orderMasterDTO={}", openid, orderMasterDTO);
			throw new SellException(ExceptionResultEnum.PARAM_ERROR);
		}
		return orderMasterDTO;
	}
}
