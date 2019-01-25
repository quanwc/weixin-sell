package com.quanwc.weixin.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.quanwc.weixin.config.ProjectUrlConfig;
import com.quanwc.weixin.exception.SellException;
import com.quanwc.weixin.exception.SellerAuthorizeException;
import com.quanwc.weixin.model.vo.ResultVO;
import com.quanwc.weixin.util.ResultVOUtil;

/**
 * 异常处理器
 * @author quanwenchao
 * @date 2019/1/21 20:09:57
 */
@ControllerAdvice
public class SellExceptionHandler {

	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	/**
	 * 拦截登录异常： 拦截的是SellerAuthorizeException这个异常，如果拦截到该异常说明未登录，则跳转到登录地址，提示卖家端扫描登录
	 *
	 * @return
	 */
	//
	@ExceptionHandler(SellerAuthorizeException.class)
	// http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
	public ModelAndView handlerAuthorizeException() {
		return new ModelAndView("redirect:" + "https://www.baidu.com");

		// 跳转到登录页面
		// return new ModelAndView("redirect:"
		// .concat(projectUrlConfig.getWechatOpenAuthorize())
		// .concat("/sell/wechat/qrAuthorize").concat("?returnUrl=")
		// .concat(projectUrlConfig.getSell().concat("/sell/seller/user/login")));
	}

	/**
	 * 处理SellException这个异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(SellException.class)
	@ResponseBody
	public ResultVO handleSellException(SellException e) {
		return ResultVOUtil.error(e.getCode(), e.getMessage());
	}

}
