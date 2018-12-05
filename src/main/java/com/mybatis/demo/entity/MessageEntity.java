package com.mybatis.demo.entity;

import java.util.Set;

public class MessageEntity {
    private String sendName;
    private String receiverName;
    private int messageType;
    private String extension;
    private String textMessage;
    private Set<String> onlineUsers;

    public Set<String>getOnlineUsers() {
        return onlineUsers;
    }

    public MessageEntity setOnlineUsers(Set<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
        return this;
    }


    public String getSendName() {
        return sendName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public MessageEntity setTextMessage(String textMessage) {
        this.textMessage = textMessage;
        return this;
    }

    public MessageEntity setSendName(String sendName) {
        this.sendName = sendName;
        return this;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public MessageEntity setReceiverName(String receiverName) {
        this.receiverName = receiverName;
        return this;
    }

    public int getMessageType() {
        return messageType;
    }

    public MessageEntity setMessageType(int messageType) {
        this.messageType = messageType;
        return this;
    }

    public String getExtension() {
        return extension;
    }

    public MessageEntity setExtension(String extension) {
        this.extension = extension;
        return this;
    }

}
