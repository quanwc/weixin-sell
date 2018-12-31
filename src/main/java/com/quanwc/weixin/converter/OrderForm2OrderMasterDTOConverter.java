package com.quanwc.weixin.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.exception.SellException;
import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.model.entity.OrderDetailDO;
import com.quanwc.weixin.model.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单form表单的请求参数converter
 * @author quanwenchao
 * @date 2018/12/29 15:26:11
 */
@Slf4j
public class OrderForm2OrderMasterDTOConverter {

	public static OrderMasterDTO orderForm2OrderMasterDTO(OrderForm param) {

		Gson gson = new Gson();

		OrderMasterDTO result = new OrderMasterDTO();
		result.setBuyerName(param.getName());
		result.setBuyerPhone(param.getPhone());
		result.setBuyerAddress(param.getAddress());
		result.setBuyerOpenid(param.getOpenid());

		// param.getItems -> List<OrderDetailDO>
		List<OrderDetailDO> orderDetailDOList = new ArrayList<>();
		try {
			orderDetailDOList = gson.fromJson(param.getItems(),
					new TypeToken<List<OrderDetailDO>>() {
					}.getType());
		}
		catch (JsonSyntaxException e) {
			log.error("[对象转换]错误, param={}", param.getItems());
			throw new SellException(ExceptionResultEnum.PARAM_ERROR);
		}

		result.setOrderDetailDOList(orderDetailDOList);
		return result;
	}

}
