package com.mybatis.demo.interceptor;

import com.mybatis.demo.constant.SessionConstant;
import com.mybatis.demo.utils.SessionUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/***
 * 拦截器
 * @author YHY
 *
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributesMap) throws Exception {
        String userName = (String) SessionUtil.getSession(SessionConstant.USER_SESSION);
        if (userName == null) {
            userName = SessionConstant.DEFAULT_USER_NAME_SESSION;
        }
        attributesMap.put(SessionConstant.WEBSOCKET_USERNAME_KEY, userName);
        return true;
    }


    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }

}
