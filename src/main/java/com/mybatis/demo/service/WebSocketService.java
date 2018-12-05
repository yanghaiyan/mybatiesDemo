package com.mybatis.demo.service;

import com.alibaba.fastjson.JSON;
import com.mybatis.demo.constant.MessageType;
import com.mybatis.demo.constant.SessionConstant;
import com.mybatis.demo.entity.MessageEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author yhy
 */
@Service
public class WebSocketService extends TextWebSocketHandler {
    private static final String SEND_ALL = "ALL";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ConcurrentHashMap<String, WebSocketSession> userMap = new ConcurrentHashMap<String, WebSocketSession>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            logger.info("来自客户端消息：" + message + "客户端的id是：" + session.getId());
            MessageEntity receiveMessage = JSON.parseObject(message.getPayload(), MessageEntity.class);
            String receiverName = receiveMessage.getReceiverName();
            //如果不是发给所有，那么就发给某一个人
            receiveMessage.setMessageType(MessageType.NORMAL.getId());
            TextMessage sendMessage = null;
            if (SEND_ALL.equals(receiverName)) {
                sendMessage = new TextMessage(JSON.toJSONString(receiveMessage));
                sendMessageAll(sendMessage);
            } else {
                sendMessage = new TextMessage(JSON.toJSONString(receiveMessage));
                sendMessageToUser(receiverName, sendMessage);
            }
        } catch (Exception e) {
            logger.error("Send error", e);
        }
    }


    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        System.out.println("有新连接加入！ 当前在线人数" + userMap.size());
        String userName = (String) session.getAttributes().get(SessionConstant.WEBSOCKET_USERNAME_KEY);
        try {

            MessageEntity sendMessage = new MessageEntity();
            sendMessage.setMessageType(MessageType.ONLINE.getId());
            sendMessage.setSendName(userName);
            TextMessage returnMessage = new TextMessage(JSON.toJSONString(sendMessage));
            sendMessageAll(returnMessage);

            //给自己发一条消息：告诉自己现在都有谁在线
            sendMessage.setMessageType(MessageType.ONLINENAMELIST.getId());
            if(userMap.size() >0) {
                sendMessage.setOnlineUsers(userMap.keySet());
            }//把自己的信息加入到map当中去
            userMap.put(userName, session);
            sendMessageToUser(userName, new TextMessage(JSON.toJSONString(sendMessage)));
        } catch (Exception e) {
            logger.info(userName + "上线错误", e);
        }

    }

    /**
     * 关闭连接时触发
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        String userName = (String) session.getAttributes().get(SessionConstant.WEBSOCKET_USERNAME_KEY);
        userMap.remove(userName);

        MessageEntity sendMessage = new MessageEntity();
        sendMessage.setSendName(userName);
        sendMessage.setMessageType(MessageType.OFFLINE.getId());
        sendMessage.setReceiverName(SEND_ALL);
        sendMessage.setOnlineUsers(userMap.keySet());
        sendMessageAll(new TextMessage(JSON.toJSONString(sendMessage)));

        System.out.println("用户" + userName + "已退出！");
        System.out.println("剩余在线用户" + userMap.size());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }

        logger.debug("websocket connection closed......");
        userMap.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        if (userMap.get(userName) != null) {
            WebSocketSession userSession = userMap.get(userName);
            if (userSession.isOpen()) {
                try {
                    userSession.sendMessage(message);
                } catch (IOException e) {
                    logger.error("Send Message fail", e);
                }
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageAll(TextMessage message) {
        for (WebSocketSession userSession : userMap.values()) {
            if (userSession.isOpen()) {
                try {
                    userSession.sendMessage(message);
                } catch (IOException e) {
                    logger.error("Send Message fail", e);
                }
            }
        }
    }


}
