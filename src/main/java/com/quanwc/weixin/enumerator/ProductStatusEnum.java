package com.quanwc.weixin.enumerator;

import lombok.Getter;

/**
 * 商品状态enum
 * @author quanwenchao
 * @date 2018/12/15 10:28:32
 */
@Getter
public enum ProductStatusEnum {

	UP(0, "上架"),
	DOWN(1, "下架"),

	;

	private Integer code;

	private String msg;

	ProductStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
