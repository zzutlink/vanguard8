package com.vanguard8.framework.controller;

import com.vanguard8.common.SessionName;
import com.vanguard8.framework.entity.Function;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.FunctionService;
import com.vanguard8.framework.vo.MenuGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/function")
public class FunctionController {

    private Logger logger = LoggerFactory.getLogger(FunctionController.class);

    @Autowired
    private FunctionService functionService;

    @RequestMapping("/getMenu")
    @ResponseBody
    public List<MenuGroup> getMenu(HttpServletRequest request) {
        //Session中取出登录人的岗位信息
        User u = (User) request.getSession().getAttribute(SessionName.USER);
        List<Function> functions = functionService.getFunctions(u.getDeptsta().getDsId());

        List<MenuGroup> menu = new ArrayList<>();
        MenuGroup g = null;
        List<Function> items = null;
        for (Function f : functions) {
            logger.debug(f.getFuncCode()+"---"+f.getFuncName());
            //当funccode.length==3，表示为分组标题，例如系统管理
            //length==6表示为下一级
            if (f.getFuncCode().length() == 3) {
                //清空，开始一组新的分组
                //若数据已经存在了则先把这一组放到菜单中
                if (g != null && g.getTitleItem() != null) {
                    menu.add(g);
                }
                g = new MenuGroup();
                g.setItems(new ArrayList<Function>());
                items = g.getItems();
                g.setTitleItem(f);
            } else {
                items.add(f);
            }
        }
        menu.add(g);
        return menu;
    }
}
