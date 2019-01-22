package com.quanwc.weixin.service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * websocket的service，用于和order/list.ftl页面中的websocket进行通信
 * @author quanwenchao
 * @date 2019/1/21 22:54:20
 */
@Slf4j
@Component
@ServerEndpoint("/webSocket")
public class WebSocketService {

	private Session session;

	/**
	 * 定义websocket容器： 用来存储Session
	 */
	private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();

	// 在前端写的一些事件，在后端有响应的对应

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		webSocketSet.add(this);
		log.info("[websockt消息] 有新的连接, 总数={}", webSocketSet.size());
	}

	@OnClose
	public void onClose() {
		webSocketSet.remove(this);
		log.info("[websocket消息] 断开连接, 总数={}", webSocketSet.size());
	}

	@OnMessage
	public void onMessage(String message) {
		log.info("[websockt消息] 收到客户端发来的消息, message={}", message);
	}

	/**
	 * 广播形式发送消息
	 * @param message
	 */
	public void sendMessage(String message) {
		for (WebSocketService webSocketService : webSocketSet) {
			log.info("[websocket消息] 广播消息, message={}", message);
			try {
				webSocketService.session.getBasicRemote().sendText(message);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
