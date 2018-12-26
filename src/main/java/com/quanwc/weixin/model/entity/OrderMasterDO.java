package com.quanwc.weixin.model.entity;

import com.quanwc.weixin.enumerator.OrderStatusEnum;
import com.quanwc.weixin.enumerator.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表
 * @author quanwenchao
 * @date 2018/12/23 22:42:55
 */
@Entity
@Data
@Table(name = "order_master")
@DynamicUpdate
public class OrderMasterDO {
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
	private Integer orderStatus = OrderStatusEnum.NEW.getCode();

	/**
	 * 支付状态，默认为0未支付
	 */
	private Integer payStatus = PayStatusEnum.WAIT.getCode();

	/**
	 * 创建时间
	 */
	private Date createTimestamp;

	/**
	 * 更新时间
	 */
	private Date updateTimestamp;
}
