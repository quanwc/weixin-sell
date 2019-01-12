package com.quanwc.weixin.controller;

import com.quanwc.weixin.enumerator.ExceptionResultEnum;
import com.quanwc.weixin.exception.SellException;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;

import java.net.URLEncoder;

/**
 * @author quanwenchao
 * @date 2019/1/5 15:20:55
 */
@Slf4j
@Controller
@RequestMapping("/wechat")
public class WechatController {

	@Autowired
	private WxMpService wxMpService;

	/**
	 * 获取openid
	 */
	@RequestMapping("/authorize")
	public String authorize(@RequestParam("returnUrl") String returnUrl) {

		String url = "http://5a39e4b2.ngrok.io/sell/wechat/userInfo";
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
			log.error("[微信网页授权]{}", e);
			throw new SellException(ExceptionResultEnum.WECHAT_MP_ERROR.getCode(),
					e.getError().getErrorMsg());
		}

		System.out.println("wxMpOAuth2AccessToken" + wxMpOAuth2AccessToken);
		String openId = wxMpOAuth2AccessToken.getOpenId();
		return "redirect:" + returnUrl + "?openid=" + openId;
	}
}
