package com.quanwc.weixin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * websocket config
 * @author quanwenchao
 * @date 2019/1/21 22:49:33
 */
@Component
public class WebSocketConfig {
	@Bean
	public ServerEndpointExporter serverEndpointExploer() {
		return new ServerEndpointExporter();
	}
}
