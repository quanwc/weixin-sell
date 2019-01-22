package com.quanwc.weixin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

import java.util.Map;

/**
 * 微信账号config
 * @author quanwenchao
 * @date 2019/1/5 15:33:20
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

	/**
	 * 公众平台appId
	 */
	private String mpAppId;

	/**
	 * 公众号appSecret
	 */
	private String mpAppSecret;

	/**
	 * 商户号
	 */
	private String mchId;

	/**
	 * 商户密钥
	 */
	private String mchKey;

	/**
	 * 商户证书路径
	 */
	private String keyPath;

	/**
	 * 微信支付异步通知地址
	 */
	private String notifyUrl;

	/**
	 * 开发平台appId
	 */
	private String openAppId;

	/**
	 * 开发平台appSecret
	 */
	private String openAppSecret;

	/**
	 * 微信模板id
	 */
	private Map<String, String> templateId;
}
