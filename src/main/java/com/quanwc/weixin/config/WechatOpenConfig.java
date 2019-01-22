package com.quanwc.weixin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * 微信开发平台config
 * @author quanwenchao
 * @date 2019/1/19 15:07:04
 */
@Component
public class WechatOpenConfig {

	@Autowired
	private WechatAccountConfig wechatAccountConfig;

	@Bean
	public WxMpService wxOpenService() {
		WxMpService wxOpenService = new WxMpServiceImpl();
		wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
		return wxOpenService;
	}

	@Bean
	public WxMpConfigStorage wxOpenConfigStorage() {
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpInMemoryConfigStorage.setAppId(wechatAccountConfig.getOpenAppId());
		wxMpInMemoryConfigStorage.setSecret(wechatAccountConfig.getOpenAppSecret());
		return wxMpInMemoryConfigStorage;
	}
}
