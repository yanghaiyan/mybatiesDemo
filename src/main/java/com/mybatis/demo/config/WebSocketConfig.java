package com.mybatis.demo.config;

import com.mybatis.demo.interceptor.WebSocketHandshakeInterceptor;
import com.mybatis.demo.service.WebSocketService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @author as
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(getWebSocketHandler(), "/wb/chat/{name}").setAllowedOrigins("*").addInterceptors(getInterceptor());
        webSocketHandlerRegistry.addHandler(getWebSocketHandler(), "/wbjs/chat/{name}").setAllowedOrigins("*").addInterceptors(getInterceptor()).withSockJS();
    }

    @Bean
    public WebSocketHandler getWebSocketHandler() {
        return new WebSocketService();
    }

    @Bean
    public HandshakeInterceptor getInterceptor() {
        return new WebSocketHandshakeInterceptor();
    }
}
