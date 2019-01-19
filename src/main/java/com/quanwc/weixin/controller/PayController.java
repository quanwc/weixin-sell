package com.quanwc.weixin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lly835.bestpay.model.PayResponse;
import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.exception.SellException;
import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.service.OrderMasterService;
import com.quanwc.weixin.service.PayService;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付controller
 * @author quanwenchao
 * @date 2019/1/8 21:19:05
 */
@Slf4j
@Controller
@RequestMapping("/pay")
public class PayController {

	@Autowired
	private OrderMasterService orderMasterService;
	@Autowired
	private PayService payService;

	/**
	 * 发起支付
	 * @param orderId 订单id
	 * @param returnUrl
	 * @param model
	 * @return
	 */
	@RequestMapping("/create")
	public ModelAndView create(@RequestParam("orderId") String orderId,
							   @RequestParam("returnUrl") String returnUrl,
							   Map<String, Object> model) {
		// 查询订单
		OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
		if (orderMasterDTO == null) {
			throw new SellException(ExceptionResultEnum.ORDER_NOT_EXIST);
		}

		// 发起支付
		PayResponse payResponse = payService.create(orderMasterDTO);
		model.put("payResponse", payResponse);
		model.put("returnUrl", returnUrl);

		return new ModelAndView("pay/create", model);
	}


	/**
	 * 微信异步通知
	 * @param notifyData 异步通知携带的数据
	 */
	@PostMapping("/notify")
	public ModelAndView notify(@RequestParam("notifyData") String notifyData) {
		//PayResponse payResponse = payService.asyncNotify(notifyData);
		System.out.println("notifyData: " + notifyData);
		// 返回给微信处理结果:
		return new ModelAndView("/pay/success");
	}
}
