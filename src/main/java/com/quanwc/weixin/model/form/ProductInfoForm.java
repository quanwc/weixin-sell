package com.quanwc.weixin.model.form;

import java.math.BigDecimal;

import lombok.Data;

/**
 * 商品的新增、修改请求表单，类似于request对象
 * @author quanwenchao
 * @date 2019/1/17 22:44:49
 */
@Data
public class ProductInfoForm {

	/**
	 * 商品id
	 */
	private String productId;

	/**
	 * 商品名称
	 */
	private String productName;

	/**
	 * 商品单价
	 */
	private BigDecimal productPrice;

	/**
	 * 商品库存
	 */
	private Integer productStock;

	/**
	 * 商品描述
	 */
	private String productDescription;

	/**
	 * 商品小图icon
	 */
	private String productIcon;

	/**
	 * 类目编号
	 */
	private Integer categoryType;
}
