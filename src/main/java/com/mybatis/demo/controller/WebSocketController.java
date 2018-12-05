package com.mybatis.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebSocketController extends BaseController {

    @RequestMapping("/chat")
    public ModelAndView webSocket() {
        ModelAndView modelAndView = new ModelAndView();
        String userName = this.getUserName();
        try {
            modelAndView.addObject("userName", userName);
            modelAndView.setViewName("chat");
        } catch (Exception e) {
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}
