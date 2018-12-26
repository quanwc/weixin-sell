package com.quanwc.weixin.model.vo;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @author quanwenchao
 * @date 2018/12/15 23:15:23
 */
@Data
public class ResultVO<T> {

	/**
	 * 错误码
	 */
	private Integer code;

	/**
	 * 提示信息
	 */
	private String msg;

	/**
	 * 具体内容
	 */
	private T data;
}
