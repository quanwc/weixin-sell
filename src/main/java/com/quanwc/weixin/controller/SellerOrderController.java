package com.quanwc.weixin.controller;

import java.util.Map;

import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.service.OrderMasterService;

/**
 * 买家订单controller
 * @author quanwenchao
 * @date 2019/1/12 10:22:39
 */
@Slf4j
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

	@Autowired
	private OrderMasterService orderMasterService;

	/**
	 * 卖家端 订单列表
	 *
	 * @param page
	 * @param limit
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "limit", defaultValue = "5") Integer limit,
			Map<String, Object> model) {
		PageRequest request = new PageRequest(page - 1, limit);
		Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.findList(request);
		model.put("orderMasterDTOPage", orderMasterDTOPage);
		model.put("currentPage", page);
		model.put("limit", limit);
		return new ModelAndView("order/list", model);
	}

	/**
	 * 取消订单
	 *
	 * @param orderId
	 * @return
	 */
	@GetMapping("/cancel")
	public ModelAndView cancel(@RequestParam("orderId") String orderId,
			Map<String, Object> model) {

		try {
			OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
			orderMasterService.cancel(orderMasterDTO);
		}
		catch (SellException e) {
			log.error("[卖家端取消订单] 发送异常{}", e);
			model.put("msg", e.getMessage());
			model.put("url", "/sell/seller/order/list");
			return new ModelAndView("common/error", model);
		}

		model.put("msg", ExceptionResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
		model.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success", model);
	}

	/**
	 * 订单详情
	 *
	 * @param orderId
	 * @param model
	 * @return
	 */
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("orderId") String orderId,
			Map<String, Object> model) {
		OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
		try {
			orderMasterDTO = orderMasterService.findOne(orderId);
		}
		catch (SellException e) {
			log.error("[卖家端查询订单详情] 发送异常{}", e);
			model.put("msg", e.getMessage());
			model.put("url", "/sell/seller/order/list");
			return new ModelAndView("common/error", model);
		}

		model.put("orderMasterDTO", orderMasterDTO);
		return new ModelAndView("order/detail", model);
	}

	/**
	 * 完结订单
	 * @param orderId
	 * @param model
	 * @return
	 */
	@GetMapping("/finish")
	public ModelAndView finish(@RequestParam("orderId") String orderId,
			Map<String, Object> model) {
		try {
			OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
			orderMasterService.finish(orderMasterDTO);
		}
		catch (SellException e) {
			log.error("[卖家端完结订单] 发送异常{}", e);
			model.put("msg", e.getMessage());
			model.put("url", "/sell/seller/order/list");
			return new ModelAndView("common/error", model);
		}

		model.put("msg", ExceptionResultEnum.ORDER_FINISH_SUCCESS.getMsg());
		model.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success", model);
	}
}