package com.mybatis.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mybatis.demo.constant.MessageType;
import com.mybatis.demo.constant.SessionConstant;
import com.mybatis.demo.entity.WebSocketEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author yhy
 */
@Service
public class WebSocketService extends TextWebSocketHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ConcurrentHashMap<String, WebSocketEntity> userMap = new ConcurrentHashMap<String, WebSocketEntity>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            logger.info("来自客户端消息：" + message + "客户端的id是：" + session.getId());
            JSONObject jsonObject = JSON.parseObject(message.getPayload());
            String textMessage = jsonObject.getString("message");
            String username = jsonObject.getString(SessionConstant.USER_SESSION);
            String tousername = jsonObject.getString("to");
            //如果不是发给所有，那么就发给某一个人
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String, Object> map1 = new HashMap();
            map1.put(SessionConstant.MESSAGE_TYPE, MessageType.NORMAL);
            map1.put("textMessage", textMessage);
            map1.put("fromusername", username);
            TextMessage sendMessage = null;
            if (tousername.equals("All")) {
                map1.put("tousername", "所有人");
                sendMessage = new TextMessage(JSON.toJSONString(map1));
                sendMessageAll(sendMessage);
            } else {
                map1.put("tousername", tousername);
                sendMessage = new TextMessage(JSON.toJSONString(map1));
                sendMessageToUser(username, sendMessage);
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
        String userName = (String) session.getAttributes().get(SessionConstant.USER_SESSION);
        try {

            WebSocketEntity webSocketEntity = new WebSocketEntity(session, MessageType.ONLINE.getId());
            TextMessage returnMessage = new TextMessage(JSON.toJSONString(webSocketEntity));
            sendMessageAll(returnMessage);
            //把自己的信息加入到map当中去
            userMap.put(userName, webSocketEntity);
            //给自己发一条消息：告诉自己现在都有谁在线
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put(SessionConstant.MESSAGE_TYPE, MessageType.ONLINENAMELIST);
            //移除掉自己
            Set<String> set = userMap.keySet();
            map2.put("onlineUsers", set);
            sendMessageToUser(userName, new TextMessage(JSON.toJSONString(map2)));
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
        Map<String, Object> map1 = new HashMap<>();
        map1.put(SessionConstant.MESSAGE_TYPE, MessageType.OFFLINE);
        map1.put(SessionConstant.ONLINE_USERS, userMap.keySet());
        map1.put(SessionConstant.USER_SESSION, userName);
        sendMessageAll(new TextMessage(JSON.toJSONString(map1)));

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
            WebSocketEntity user = userMap.get(userName);
            if (user.getWebSocketSession().isOpen()) {
                try {
                    user.getWebSocketSession().sendMessage(message);
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
        for (WebSocketEntity user : userMap.values()) {
            if (user.getWebSocketSession().isOpen()) {
                try {
                    user.getWebSocketSession().sendMessage(message);
                } catch (IOException e) {
                    logger.error("Send Message fail", e);
                }
            }
        }
    }


}
