package com.vanguard8.framework.controller;

import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.common.SessionName;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/changePwd")
    public String changePwd() {
        return "/sysmana/changePwd";
    }

    @RequestMapping("/user")
    public String user(){
        return "/sysmana/user";
    }

    @RequestMapping("/submitChange")
    @ResponseBody
    public Result<String> submitChange(HttpServletRequest request, String oldPassword,String newPassword,String confirmPassword) {
        Result<String> r = null;
        if(newPassword.equals(confirmPassword)){
            User user = (User)request.getSession().getAttribute(SessionName.USER);
            r = userService.changePwd(user.getUserId(),oldPassword,newPassword);
        }
        else{
            r = ResultGenerator.genFailResult("新密码与确认密码不匹配，请重新输入！");
        }
        return r;
    }
}
