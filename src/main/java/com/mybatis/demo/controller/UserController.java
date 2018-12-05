package com.mybatis.demo.controller;

import com.mybatis.demo.constant.SessionConstant;
import com.mybatis.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController extends BaseController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }



    @PostMapping(value = "/user/login")
    // 对登录请求判断request的参数值，并存放在map中
    public ModelAndView login(@RequestParam("userName") String userName,
                              @RequestParam("password") String password,
                              HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (userService.isLogin(userName, password)) {
            // 登录成功，就跳转到下一个页面
            this.addSession(SessionConstant.USER_SESSION, userName);
            RedirectView redirectView = new RedirectView("/chat");
            modelAndView.setView(redirectView);
          //  modelAndView.setViewName("/chat");
            request.getSession().setAttribute(SessionConstant.USER_SESSION,userName);
        } else {
            // 登录失败，刷新本登录页
            modelAndView.addObject("msg", "用户名密码错误");
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }


}
