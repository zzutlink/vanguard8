package com.vanguard8.framework.controller;

import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.common.SessionName;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.UserService;
import com.vanguard8.util.ValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    //跳转登录页
    @RequestMapping(value = "/index")
    public String index() {
        logger.debug("login/index");
        return "/login/index";
    }

    //退出登录
    @RequestMapping("/logout")
    @ResponseBody
    public Result<String> logout(HttpServletRequest request) {
        //清空session，跳转到登录页面
        request.getSession().removeAttribute(SessionName.USER);
        Result<String> r = ResultGenerator.genSuccessResult();
        return r;
    }

    //返回类型为图片，通过response输出流返回
    @RequestMapping(value = "/code", produces = "image/jpeg")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.debug("/login/code");
        ValidateCode v = new ValidateCode();     //用我们的验证码类，生成验证码类对象
        BufferedImage image = v.getImage();  //获取验证码
        request.getSession().setAttribute(SessionName.CHECK_CODE, v.getText().toLowerCase()); //将验证码的文本存在session中
        ImageIO.write(image, "jpg", response.getOutputStream());//将验证码图片响应给客户端
    }

    @RequestMapping("/check")
    @ResponseBody
    public Result<String> loginCheck(HttpServletRequest request, @RequestParam String loginName, String password, String checkCode) {
        logger.debug(loginName + "----" + password);
        Result<String> r2;
        String srvCode = request.getSession().getAttribute(SessionName.CHECK_CODE).toString();
        if (srvCode.toLowerCase().equals(checkCode.toLowerCase())) {
            Result<User> r = userService.login(loginName, password);
            r2 = new Result<String>(r.isSuccess(), r.getResultCode(), r.getMessage());
            if (r.isSuccess()) {
                request.getSession().setAttribute(SessionName.USER, r.getData());
            }
        } else {
            r2 = ResultGenerator.genFailResult("验证码输入错误！");
        }
        return r2;
    }
}
