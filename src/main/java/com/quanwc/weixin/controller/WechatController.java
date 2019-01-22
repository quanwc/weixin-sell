package com.quanwc.weixin.controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quanwc.weixin.config.ProjectUrlConfig;
import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.exception.SellException;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

/**
 * 微信controller
 * @author quanwenchao
 * @date 2019/1/19 15:28:34
 */
@Slf4j
@Controller("/wechat")
public class WechatController {

	@Autowired
	private WxMpService wxMpService;
	@Autowired
	private WxMpService wxOpenService;
	@Autowired
	private ProjectUrlConfig projectUrlConfig;

	@GetMapping("/authorize")
	public String authorize(@RequestParam("returnUrl") String returnUrl) {
		// 1. 配置
		// 2. 调用方法
		String url = projectUrlConfig.getWechatMpAuthorize() + "/wechat/userInfo";
		String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url,
				WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl));
		return "redirect:" + redirectUrl;
	}

	@GetMapping("/userInfo")
	public String userInfo(@RequestParam("code") String code,
			@RequestParam("state") String returnUrl) {
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
		try {
			wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
		}
		catch (WxErrorException e) {
			log.error("【微信网页授权】{}", e);
			throw new SellException(ExceptionResultEnum.WECHAT_MP_ERROR.getCode(),
					e.getError().getErrorMsg());
		}

		String openId = wxMpOAuth2AccessToken.getOpenId();

		return "redirect:" + returnUrl + "?openid=" + openId;
	}

	// 微信开发平台：二维码（QR code）登录
	/**
	 * 扫描登录的地址
	 * @param returnUrl 扫码后的跳转地址为登录地址 @see:
	 * http://sell.natapp4.cc/sell/wechat/qrAuthorize?returnUrl=http://sell.natapp4.cc/sell/seller/login
	 * @return
	 */
	@GetMapping("/qrAuthorize")
	public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
		String url = projectUrlConfig.getWechatOpenAuthorize() + "/wechat/qrUserInfo";
		String redirectUrl = wxOpenService.buildQrConnectUrl(url,
				WxConsts.QrConnectScope.SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
		return "redirect:" + redirectUrl;
	}

	@GetMapping("/qrUserInfo")
	public String qrUserInfo(@RequestParam("code") String code,
			@RequestParam("state") String returnUrl) {
		WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

		try {
			wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
		}
		catch (WxErrorException e) {
			log.error("[微信网页授权]{}", e);
			throw new SellException(ExceptionResultEnum.WECHAT_MP_ERROR.getCode(),
					e.getError().getErrorMsg());
		}

		String openId = wxMpOAuth2AccessToken.getOpenId();

		return "redirect:" + returnUrl + "?openid" + openId;
	}
}
