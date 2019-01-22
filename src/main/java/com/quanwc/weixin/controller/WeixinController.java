package com.quanwc.weixin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信controller（暂时不用）
 * @author quanwenchao
 * @date 2019/1/5 14:10:20
 */
@Slf4j
@RestController
@RequestMapping("/weixin")
public class WeixinController {

	@RequestMapping("/auth")
	public void auth(@RequestParam("code") String code) {
		log.info("auth method...");
		log.info("code={}", code);

		//String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxb7855b4907d34564&secret=0cae0c81c3d001b0cf35a0da8fa4950f&code="
		//		+ code + "&grant_type=authorization_code";
		//RestTemplate restTemplate = new RestTemplate();
		//String response = restTemplate.getForObject(url, String.class);
		//log.info("response={}", response);
	}

}
