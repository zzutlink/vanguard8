package com.vanguard8.framework.controller;

import com.vanguard8.common.SessionName;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.DictService;
import com.vanguard8.framework.vo.Dict;
import com.vanguard8.framework.vo.DictCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static sun.net.www.ParseUtil.decode;

@Controller
@RequestMapping("/dict")
public class DictController {
    @Autowired
    private DictService dictService;

    @RequestMapping("/getDict")
    @ResponseBody
    public List<Dict> getDict(HttpServletRequest request, @RequestParam(defaultValue = "0") Integer flag, @RequestParam(defaultValue = "") String condition){
        DictCondition c = new DictCondition();
        c.setFlag(flag);
        c.setCondition(decode(condition));

        User u = (User)request.getSession().getAttribute(SessionName.USER);
        c.setUserId(u.getUserId());

        return dictService.getDict(c);
    }
}
