package com.quanwc.weixin.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.exception.SellException;
import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.service.OrderMasterService;
import com.quanwc.weixin.service.PayService;
import com.quanwc.weixin.util.JsonUtil;
import com.quanwc.weixin.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 支付impl
 *
 * @author quanwenchao
 * @date 2019/1/8 21:21:53
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {

	private static final String ORDER_NAME = "微信支付订单";

	@Autowired
	private BestPayServiceImpl bestPayService;
	@Autowired
	private OrderMasterService orderMasterService;

	/**
	 * 发起支付
	 * @param orderMasterDTO 订单信息
	 * @return
	 */
	@Override
	public PayResponse create(OrderMasterDTO orderMasterDTO) {

		PayRequest payRequest = new PayRequest();
		payRequest.setOpenid(orderMasterDTO.getBuyerOpenid());
		payRequest.setOrderAmount(orderMasterDTO.getOrderAmount().doubleValue());
		payRequest.setOrderId(orderMasterDTO.getOrderId());
		payRequest.setOrderName(ORDER_NAME);
		payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
		log.info("[微信支付] 发起支付, request={}", JsonUtil.object2Json(payRequest));

		PayResponse payResponse = bestPayService.pay(payRequest);
		log.info("[微信支付] 发起支付, response={}", JsonUtil.object2Json(payResponse));

		return payResponse;
	}

	/**
	 * 微信异步通知：
	 * step1：验证签名，验证该消息是否是微信发送过来的
	 * step2：支付的状态：订单的支付状态，eg：订单未支付、支付失败等
	 * step3：支付金额，判断订单金额和微信回调的金额是否一致
	 * step4：支付人（下单人 == 支付人），没有代付的功能
	 * @param asyNotifyData
	 * @return
	 */
	@Override
	public PayResponse asyncNotify(String asyNotifyData) {

		PayResponse payResponse = bestPayService.asyncNotify(asyNotifyData);
		log.info("[微信支付] 异步通知, payResponse={}", JsonUtil.object2Json(payResponse));

		// 查询订单
		OrderMasterDTO orderMasterDTO = orderMasterService
				.findOne(payResponse.getOrderId());
		if (orderMasterDTO == null) {
			log.error("[微信支付] 异步通知, 订单不存在, orderId={}", payResponse.getOrderId());
			throw new SellException(ExceptionResultEnum.ORDER_NOT_EXIST);
		}

		// 判断金额是否一致
		if (!MathUtil.equals(payResponse.getOrderAmount(),
				orderMasterDTO.getOrderAmount().doubleValue())) {
			log.error("[微信支付] 异步通知, 订单金额不一致, orderId={}, 微信通知金额={}, 系统金额={}",
					payResponse.getOrderId(), payResponse.getOrderAmount(),
					orderMasterDTO.getOrderAmount());
			throw new SellException(ExceptionResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
		}

		// 修改订单的支付状态
		orderMasterService.paid(orderMasterDTO);

		return payResponse;
	}


	/**
	 * 微信退款
	 * @param orderMasterDTO
	 * @return
	 */
	@Override
	public RefundResponse refund(OrderMasterDTO orderMasterDTO) {

		RefundRequest refundRequest = new RefundRequest();
		refundRequest.setOrderId(orderMasterDTO.getOrderId());
		refundRequest.setOrderAmount(orderMasterDTO.getOrderAmount().doubleValue());
		refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
		log.info("[微信退款] request={}", JsonUtil.object2Json(refundRequest));

		RefundResponse refundResponse = bestPayService.refund(refundRequest);
		log.info("[微信退款] response={}", JsonUtil.object2Json(refundResponse));
		return refundResponse;
	}
}
