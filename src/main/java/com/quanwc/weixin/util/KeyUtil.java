package com.quanwc.weixin.util;

import java.util.Random;

/**
 * @author quanwenchao
 * @date 2018/12/25 15:29:33
 */
public class KeyUtil {

	/**
	 * 生成唯一的主键
	 * 格式：时间 + 随机数(6位)
	 * @return
	 */
	public static synchronized String generateUniqueKey() {
		Random random = new Random();
		Integer number = random.nextInt(900000) + 100000;
		return System.currentTimeMillis() + String.valueOf(number);
	}

}
