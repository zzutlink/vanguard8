package com.vanguard8.framework.controller;

import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.SessionName;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.DictService;
import com.vanguard8.framework.service.UserService;
import com.vanguard8.framework.vo.Dict;
import com.vanguard8.framework.vo.DictCondition;
import com.vanguard8.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private DictService dictService;

    @RequestMapping("/getUsers")
    public EasyUIDataGrid getUsers(){
        User user = new User();
        user.setUserName("");
        user.setLoginName("");
        return userService.getUsers(1,20,"","", user);
    }

    @RequestMapping("/getDict")
    public List<Dict> getDict(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer flag, @RequestParam(defaultValue = "") String condition){
        DictCondition c = new DictCondition();
        c.setFlag(flag);
        c.setCondition(condition);

        return dictService.getDict(c);
    }

    @RequestMapping("/md52")
    public String md5(String salt, String pwd){
        return MD5Util.MD5Encode(pwd.concat(salt));
    }
}
