package com.quanwc.weixin.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 订单详情
 * @author quanwenchao
 * @date 2018/12/23 22:43:35
 */
@Entity
@Table(name = "order_detail")
@Data
public class OrderDetailDO {

	/**
	 * 订单详情id
	 */
	@Id
	private String detailId;

	/**
	 * 订单id
	 */
	private String orderId;

	/**
	 * 商品id
	 */
	private String productId;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品价格
	 */
	private BigDecimal productPrice;

	/**
	 * 商品数量
	 */
	private Integer productQuantity;

	/**
	 * 商品小图icon
	 */
	private String productIcon;
}
