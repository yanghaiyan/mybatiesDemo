package com.mybatis.demo.entity;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketEntity {
    private WebSocketSession webSocketSession;
    private int MessageType;

    public WebSocketEntity(WebSocketSession webSocketSession, int messageType) {
        this.webSocketSession = webSocketSession;
        MessageType = messageType;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public WebSocketEntity setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
        return this;
    }


    public int getMessageType() {

        return MessageType;
    }

    public WebSocketEntity setMessageType(int messageType) {
        MessageType = messageType;
        return this;
    }


}
