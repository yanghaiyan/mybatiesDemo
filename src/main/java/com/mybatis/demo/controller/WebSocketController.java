package com.mybatis.demo.controller;

import com.mybatis.demo.constant.SessionConstant;
import com.mybatis.demo.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class WebSocketController {

    @Autowired
    public WebSocketService webSocketService;

    @RequestMapping("/wb/send")
    @ResponseBody
    public String send(HttpServletRequest request) {
        String username = request.getParameter("username");
        webSocketService.sendMessageToUser(username, new TextMessage("你好，测试！！！！"));
        return null;
    }
}
