package com.quanwc.weixin.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 商品详情
 * @author quanwenchao
 * @date 2018/12/15 9:26:27
 */
@Entity
@Table(name = "product_info")
@Data
public class ProductInfo {

	/**
	 * 商品id
	 */
	@Id
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
	 * 商品状态：0正常1下架
	 */
	private Integer productStatus;

	/**
	 * 类目编号
	 */
	private Integer categoryType;

}
