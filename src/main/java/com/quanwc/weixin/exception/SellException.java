package com.quanwc.weixin.exception;

import com.quanwc.weixin.enumerator.ExceptionResultEnum;

/**
 * 自定义异常类
 * @author quanwenchao
 * @date 2018/12/25 14:35:04
 */
public class SellException extends  RuntimeException {

	private Integer code;

	public SellException(ExceptionResultEnum resultEnum) {
		super(resultEnum.getMsg());
		this.code = resultEnum.getCode();
	}
}
