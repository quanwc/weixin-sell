package com.quanwc.weixin.enumerator;

import lombok.Getter;

/**
 * 异常enum
 * @author quanwenchao
 * @date 2018/12/25 14:34:27
 */
@Getter
public enum ExceptionResultEnum {

	SUCCESS(0, "成功"),

	PARAM_ERROR(1, "参数错误"),

	PRODUCT_NOT_EXIST(9, "商品不存在"),

	PRODUCT_STOCK_ERROR(10, "商品库存错误"),

	PRODUCT_STATUS_ERROR(11, "商品状态错误"),

	ORDER_NOT_EXIST(12, "订单不存在"),

	ORDER_DETAIL_NOT_EXIST(13, "订单详情不存在"),

	ORDER_STATUS_ERROR(14, "订单状态错误"),

	ORDER_UPDATE_FAIL(15, "订单更新失败"),

	ORDER_DETAIL_EMPTY(16, "订单详情为空"),

	ORDER_PAY_STATUS_ERROR(17, "订单支付状态错误"),

	SHOP_CAR_EMPTY_ERROR(18, "购物车不能为空"),

	ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),

	WECHAT_MP_ERROR(21, "微信授权错误"),

	WXPAY_NOTIFY_MONEY_VERIFY_ERROR(22, "微信支付异步通知金额校验不通过"),

	ORDER_CANCEL_SUCCESS(23, "订单取消成功"),

	ORDER_FINISH_SUCCESS(24, "订单完结成功"),

	LOGIN_FAIL(25, "登录失败，登录信息不正确"),

	LOGOUT_SUCCESS(26, "登录成功"),

	;

	private Integer code;

	private String msg;

	ExceptionResultEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
