package com.vanguard8.framework.controller;

import com.vanguard8.common.SessionName;
import com.vanguard8.framework.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/main")
public class MainController {

    @RequestMapping("/main")
    public String main(HttpServletRequest request,Model model){
        User user = (User)request.getSession().getAttribute(SessionName.USER);
        model.addAttribute("userName",user.getUserName());
        model.addAttribute("dsTotalName",user.getDeptsta().getDsTotalName());
        return "/main/main";
    }
}
