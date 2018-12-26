package com.quanwc.weixin.model.dto;

import lombok.Data;

/**
 * 购物车dto
 * @author quanwenchao
 * @date 2018/12/25 15:55:31
 */
@Data
public class ShopCartDTO {
	/**
	 * 商品id
	 */
	private String productId;

	/**
	 * 数量
	 */
	private Integer productQuantity;

	public ShopCartDTO(String productId, Integer productQuantity) {
		this.productId = productId;
		this.productQuantity = productQuantity;
	}
}
