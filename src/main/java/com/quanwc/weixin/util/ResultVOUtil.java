package com.quanwc.weixin.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.quanwc.weixin.model.vo.ResultVO;

/**
 * 返回结果util
 * @author quanwenchao
 * @date 2018/12/16 22:02:05
 */
public class ResultVOUtil {

	public static ResultVO success(Object object) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(0);
		resultVO.setMsg("success");
		resultVO.setData(object);
		return resultVO;
	}

	public static ResultVO success() {
	 	return success(null);
	}

	public static ResultVO error(Integer code, String msg) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(code);
		resultVO.setMsg(msg);
		return resultVO;
	}

}
