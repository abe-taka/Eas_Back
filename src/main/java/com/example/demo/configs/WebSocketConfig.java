package com.example.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// Websocket設定クラス
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	// メッセージブローカーの設定
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 1対多
		config.enableSimpleBroker("/queue","/topic");
		// プレフィックスの定義
		config.setApplicationDestinationPrefixes("/socket_prefix");
	}

	// エンドポイントの設定
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/socket_endpoint").withSockJS();
	}
}