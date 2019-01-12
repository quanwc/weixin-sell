package com.quanwc.weixin.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author quanwenchao
 * @date 2019/1/9 22:18:25
 */
public class JsonUtil {

	/**
	 * 对象转json
	 * @param object
	 * @return
	 */
	public static String object2Json(Object object) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		return gson.toJson(object);
	}

}
