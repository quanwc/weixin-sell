package com.quanwc.weixin.enumerator;

import lombok.Getter;

/**
 * 异常enum
 * @author quanwenchao
 * @date 2018/12/25 14:34:27
 */
@Getter
public enum ExceptionResultEnum {

	PRODUCT_NOT_EXIST(10, "商品不存在"),

	PRODUCT_STOCK_ERROR(11, "商品库存错误"),

	ORDER_NOT_EXIST(12, "订单不存在"),

	ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),

	ORDER_STATUS_ERROR(14, "订单状态错误"),

	ORDER_UPDATE_FAIL(15, "订单更新失败"),

	ORDER_DETAIL_EMPTY(16, "订单详情为空"),

	ORDER_PAY_STATUS_ERROR(17, "订单支付状态错误"),

	;

	private Integer code;

	private String msg;

	ExceptionResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
