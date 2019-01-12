package com.quanwc.weixin.util;

/**
 * @author quanwenchao
 * @date 2019/1/10 22:12:27
 */
public class MathUtil {

	private static final Double MONEY_RANGE = 0.01;

	/**
	 * 比较是否相等：如果d1、d2差值小于MONEY_RANGE，则相等
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Boolean equals(Double d1, Double d2) {
		Double result = Math.abs(d1 - d2);
		if (result < MONEY_RANGE) {
			return true;
		}
		else {
			return false;
		}
	}

}
