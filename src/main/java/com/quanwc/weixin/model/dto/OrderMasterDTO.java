package com.quanwc.weixin.model.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import com.quanwc.weixin.model.entity.OrderDetailDO;

import lombok.Data;

/**
 * 订单主表dto
 * @author quanwenchao
 * @date 2018/12/25 11:15:17
 */
@Data
public class OrderMasterDTO {
	/**
	 * 订单id
	 */
	@Id
	private String orderId;

	/**
	 * 买家名字
	 */
	private String buyerName;

	/**
	 * 买家电话
	 */
	private String buyerPhone;

	/**
	 * 买家地址
	 */
	private String buyerAddress;

	/**
	 * 买家微信openid
	 */
	private String buyerOpenid;

	/**
	 * 订单总额
	 */
	private BigDecimal orderAmount;

	/**
	 * 订单状态，默认为0新下单
	 */
	private Integer orderStatus;

	/**
	 * 支付状态，默认为0未支付
	 */
	private Integer payStatus;

	/**
	 * 创建时间
	 */
	private Date createTimestamp;

	/**
	 * 更新时间
	 */
	private Date updateTimestamp;

	/**
	 * 订单详情
	 */
	List<OrderDetailDO> orderDetailDOList;
}
