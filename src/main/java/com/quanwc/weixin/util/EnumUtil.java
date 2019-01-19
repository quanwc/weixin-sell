package com.quanwc.weixin.util;

import com.quanwc.weixin.enumerator.CodeEnum;

/**
 * 枚举util
 * @author quanwenchao
 * @date 2019/1/12 11:35:04
 */
public class EnumUtil {

	/**
	 * 根据code、返回code对应Class的枚举类
	 * @param code
	 * @param enumClass
	 * @param <T>
	 * @return
	 */
	public static <T extends CodeEnum> T getEnumByCode(Integer code, Class<T> enumClass) {
		for (T each : enumClass.getEnumConstants()) {
			if (each.getCode().equals(code)) {
				return each;
			}
		}
		return null;
	}
}
