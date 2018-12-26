package com.quanwc.weixin.converter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.quanwc.weixin.model.dto.OrderMasterDTO;
import com.quanwc.weixin.model.entity.OrderMasterDO;

/**
 * 订单主表converter
 * @author quanwenchao
 * @date 2018/12/26 10:20:02
 */
public class OrderMaster2OrderMasterDTOConverter {

	/**
	 * OrderMasterDO 转为 OrderMasterDTO
	 * @param param
	 * @return
	 */
	public static OrderMasterDTO orderMasterDO2OrderMasterDTO(OrderMasterDO param) {
		OrderMasterDTO result = new OrderMasterDTO();
		BeanUtils.copyProperties(param, result);
		return result;
	}

	/**
	 * List<OrderMasterDO> 转为 List<OrderMasterDTO>
	 * @param paramList
	 * @return
	 */
	public static List<OrderMasterDTO> orderMasterDOList2OrderMasterDTOList(
			List<OrderMasterDO> paramList) {

		// return paramList.stream().map(e -> orderMasterDO2OrderMasterDTO(e))
		// .collect(Collectors.toList());

		// return paramList.stream().map(e -> {
		// return orderMasterDO2OrderMasterDTO(e);
		// }).collect(Collectors.toList());

		return paramList.stream()
				.map(OrderMaster2OrderMasterDTOConverter::orderMasterDO2OrderMasterDTO)
				.filter(obj -> !Objects.isNull(obj)).collect(Collectors.toList());

	}

}
