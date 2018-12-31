package com.quanwc.weixin.model.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.quanwc.weixin.model.entity.OrderDetailDO;

import com.quanwc.weixin.util.serializer.Date2LongSerializer;
import lombok.Data;

/**
 * 订单主表dto
 * @author quanwenchao
 * @date 2018/12/25 11:15:17
 */
@Data
// @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) // 属性值为null的话，该属性就不返回了。
// eg：orderDetailDOList为null的话，改属性就不返回了
// @JsonInclude(JsonInclude.Include.NON_NULL) // 只对当前类生效，我们可以在配置文件设置全局配置，来对所有类都生效
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
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date createTimestamp;

	/**
	 * 更新时间
	 */
	@JsonSerialize(using = Date2LongSerializer.class)
	private Date updateTimestamp;

	/**
	 * 订单详情
	 */
	List<OrderDetailDO> orderDetailDOList;
}
