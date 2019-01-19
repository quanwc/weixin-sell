package com.quanwc.weixin.enumerator;

import lombok.Getter;

/**
 * 支付状态enum
 * @author quanwenchao
 * @date 2018/12/25 9:41:44
 */
@Getter
public enum PayStatusEnum implements CodeEnum{
	WAIT(0, "等待支付"),
	SUCCESS(1, "支付成功"),

	;

	private Integer code;

	private String msg;

	PayStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 根据code，获取对应的枚举类
	 * @param code
	 * @return
	 */
	public static PayStatusEnum getPayStatusEnum(Integer code) {
		for (PayStatusEnum payStatusEnum : PayStatusEnum.values()) {
			if (payStatusEnum.getCode().equals(code)) {
				return payStatusEnum;
			}
		}
		return null;
	}
}
