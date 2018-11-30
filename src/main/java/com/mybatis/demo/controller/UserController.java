package com.mybatis.demo.controller;

import com.mybatis.demo.constant.SessionConstant;
import com.mybatis.demo.entity.User;
import com.mybatis.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public String save(User user) {
        userService.save(user);
        return "save success";
    }

    @PutMapping("{userName}")
    public String update(User user, @PathVariable String userName) {
        if (userService.findByUserName(userName) == null) {
            return "Not Exist";
        }

        if (user.getUserName() != userName) {
            return "Unmatched parameters";
        }
        userService.update(user);
        return "update success";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "delete success";
    }

    @PostMapping(value = "/user/login")
    // 对登录请求判断request的参数值，并存放在map中
    public ModelAndView login(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              Map<String, Object> map) {
        ModelAndView modelAndView = new ModelAndView();
        // 我们判断，如果登录名不是空，并且，密码是 123456 就登录成功（暂不涉及数据库）
        if (userService.isLogin(username, password)) {
            // 登录成功，就跳转到下一个页面
            this.addSession(SessionConstant.USER_SESSION, username);
            modelAndView.setViewName("dashboard");
        } else {
            // 登录失败，刷新本登录页
            map.put("msg", "用户名密码错误");
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    //使用get方法查询
    @GetMapping()
    public List<User> list() {
        return userService.list();
    }

    //使用get方法查询单个数据
    @GetMapping("{id}")
    public User getById(@PathVariable String userName) {
        return userService.findByUserName(userName);
    }

}
