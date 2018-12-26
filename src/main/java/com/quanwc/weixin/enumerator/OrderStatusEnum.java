package com.quanwc.weixin.enumerator;

import lombok.Getter;

/**
 * 订单状态enum
 * @author quanwenchao
 * @date 2018/12/25 9:36:44
 */
@Getter
public enum OrderStatusEnum {
	NEW(0, "新订单"),

	FINISHED(1, "完结"),

	CANCEL(2, "已取消"),

	;

	private Integer code;

	private String msg;

	OrderStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
