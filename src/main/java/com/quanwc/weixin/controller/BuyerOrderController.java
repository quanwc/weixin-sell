package com.quanwc.weixin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.quanwc.weixin.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.quanwc.weixin.converter.OrderForm2OrderMasterDTOConverter;
import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.exception.SellException;
import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.model.form.OrderForm;
import com.quanwc.weixin.model.vo.ResultVO;
import com.quanwc.weixin.service.OrderMasterService;
import com.quanwc.weixin.util.ResultVOUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 买家端订单controller
 *
 * @author quanwenchao
 * @date 2018/12/29 14:47:26
 */
@Slf4j
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

	@Autowired
	private OrderMasterService orderMasterService;
	@Autowired
	private BuyerService buyerService;

	/**
	 * 创建订单
	 *
	 * @param orderForm
	 * @return
	 */
	@PostMapping("/create")
	public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			log.error("[创建订单] 参数不正确, orderForm={}", orderForm);
			throw new SellException(ExceptionResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}

		OrderMasterDTO orderMasterDTO = OrderForm2OrderMasterDTOConverter
				.orderForm2OrderMasterDTO(orderForm);
		if (CollectionUtils.isEmpty(orderMasterDTO.getOrderDetailDOList())) {
			log.error("[创建订单] 购物车不能为空");
			throw new SellException(ExceptionResultEnum.SHOP_CAR_EMPTY_ERROR);
		}

		OrderMasterDTO createResult = orderMasterService.create(orderMasterDTO);
		Map<String, String> map = new HashMap<>();
		map.put("orderId", createResult.getOrderId());
		return ResultVOUtil.success(map);
	}

	/**
	 * 订单列表
	 *
	 * @param openid 微信openid
	 * @param page 分页参数page
	 * @param limit 分页参数limit
	 * @return
	 */
	@GetMapping("/list")
	public ResultVO<List<OrderMasterDTO>> list(
			@RequestParam(value = "openid") String openid,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "15") Integer limit) {

		if (StringUtils.isEmpty(openid)) {
			log.error("[查询订单列表]openid为空");
			throw new SellException(ExceptionResultEnum.PARAM_ERROR);
		}

		PageRequest pageRequest = new PageRequest(page, limit);
		Page<OrderMasterDTO> orderMasterDTOPage = orderMasterService.findList(openid,
				pageRequest);

		return ResultVOUtil.success(orderMasterDTOPage.getContent());
	}

	/**
	 * 订单详情
	 * @param openid
	 * @param orderId
	 * @return
	 */
	@GetMapping("/detail")
	public ResultVO<OrderMasterDTO> detail(@RequestParam("openid") String openid,
			@RequestParam("orderId") String orderId) {
		OrderMasterDTO orderMasterDTO = buyerService.findOrderOne(openid, orderId);
		return ResultVOUtil.success(orderMasterDTO);
	}

	/**
	 * 取消订单
	 */
	@PostMapping("/cancel")
	public ResultVO cancal(@RequestParam("openid") String openid,
			@RequestParam("orderId") String orderId) {
		buyerService.cancelOrder(openid, orderId);
		return ResultVOUtil.success();
	}
}
