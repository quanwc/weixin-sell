package com.quanwc.weixin.service;

import com.quanwc.weixin.model.dto.OrderMasterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 订单主表service接口层
 * @author quanwenchao
 * @date 2018/12/25 11:17:17
 */
public interface OrderMasterService {

	/**
	 * 创建订单
	 * @param orderMasterDTO
	 * @return
	 */
	OrderMasterDTO create(OrderMasterDTO orderMasterDTO);

	/**
	 * 查询单个订单
	 * @param orderId 订单id
	 * @return
	 */
	OrderMasterDTO findOne(String orderId);

	/**
	 * 查询订单列表，带分页功能
	 * @param buyerOpenid 买家微信openid
	 * @param pageable 分页参数
	 * @return Page<OrderMasterDTO>
	 */
	Page<OrderMasterDTO> findList(String buyerOpenid, Pageable pageable);

	/**
	 * 取消订单
	 * @param orderMasterDTO
	 * @return
	 */
	OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO);

	/**
	 * 完结订单
	 * @param orderMasterDTO
	 * @return
	 */
	OrderMasterDTO finish(OrderMasterDTO orderMasterDTO);

	/**
	 * 支付订单
	 * @param orderMasterDTO
	 * @return
	 */
	OrderMasterDTO paid(OrderMasterDTO orderMasterDTO);

	/**
	 * 订单列表（后台使用）
	 * @param pageable
	 * @return
	 */
	Page<OrderMasterDTO> findList(Pageable pageable);

}
