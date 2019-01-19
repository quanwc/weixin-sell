package com.quanwc.weixin.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quanwc.weixin.enumerator.ProductStatusEnum;
import com.quanwc.weixin.util.EnumUtil;

import lombok.Data;

/**
 * 商品详情
 * @author quanwenchao
 * @date 2018/12/15 9:26:27
 */
@Entity
@Table(name = "product_info")
@Data
@DynamicUpdate
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
	 * 商品状态：0上架1下架
	 */
	private Integer productStatus = ProductStatusEnum.UP.getCode();

	/**
	 * 类目编号
	 */
	private Integer categoryType;

	/**
	 * 创建时间
	 */
	private Date createTimestamp;

	/**
	 * 修改时间
	 */
	private Date updateTimestamp;


	@JsonIgnore
	public ProductStatusEnum getProductStatusEnum() {
		return EnumUtil.getEnumByCode(productStatus, ProductStatusEnum.class);
	}
}
