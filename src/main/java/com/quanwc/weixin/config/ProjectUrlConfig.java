package com.quanwc.weixin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目的配置类
 * @author quanwenchao
 * @date 2019/1/20 15:57:02
 */
@Data
@ConfigurationProperties("projectUrl")
@Component
public class ProjectUrlConfig {
	/**
	 * 微信公众平台授权url
	 */
	public String wechatMpAuthorize;

	/**
	 * 微信开放平台授权url
	 */
	public String wechatOpenAuthorize;

	/**
	 * 点餐系统
	 */
	public String sell;
}
