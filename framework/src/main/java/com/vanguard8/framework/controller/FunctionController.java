package com.vanguard8.framework.controller;

import com.vanguard8.common.*;
import com.vanguard8.framework.entity.Action;
import com.vanguard8.framework.entity.Function;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.FunctionService;
import com.vanguard8.framework.vo.MenuGroup;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/mana")
    public String mana() {
        return "/core/function";
    }

    @RequestMapping("/getMenu")
    @ResponseBody
    public List<MenuGroup> getMenu(HttpServletRequest request) {
        //Session中取出登录人的岗位信息
        SessionUser u = (SessionUser) request.getSession().getAttribute(SessionName.USER);
        List<Function> functions = functionService.getFunctions(u.getDsId());

        List<MenuGroup> menu = new ArrayList<>();
        MenuGroup g = null;
        List<Function> items = null;
        for (Function f : functions) {
            logger.debug(f.getFuncCode() + "---" + f.getFuncName());
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

    @RequestMapping("/getLevelFunctions")
    @ResponseBody
    public List<TreeNode> getLevelFunctions(@RequestParam(defaultValue = "0") String id) {
        List<Function> functions = functionService.getLevelFunctions(Integer.valueOf(id));
        List<TreeNode> tree = new ArrayList<TreeNode>();
        for (int i = 0; i < functions.size(); i++) {
            TreeNode node = new TreeNode();
            Function function = functions.get(i);
            node.setId(function.getFuncId().toString());
            node.setText(function.getFuncName());
            node.setDsCode(function.getFuncCode());
            node.setAttributes(function);
            if (function.getIsLast() == 0) {
                node.setState("closed");
            } else {
                node.setState("open");
            }
            tree.add(node);
        }
        return tree;
    }

    @RequestMapping("/saveFunction")
    @ResponseBody
//    public Result<String> saveFunction(String playFlag, Function function){
    public Result<String> saveFunction(String playFlag, String funcId, Byte isLast, String funcName, String funcCode,
                                       @RequestParam(defaultValue = "") String funcPath, Integer orderValue) {
        Function function = new Function();
        if (playFlag.equals("1")) {
            function.setFuncId(0);
        } else {
            function.setFuncId(Integer.valueOf(funcId));
        }
        function.setIsLast(isLast);
        function.setFuncName(funcName);
        function.setFuncCode(funcCode);
        function.setFuncPath(funcPath);
        function.setOrderValue(orderValue);

        Result<String> r = functionService.saveFunction(playFlag, function);
        return r;
    }

    @RequestMapping("/getFuncActions")
    @ResponseBody
    public EasyUIDataGrid getFuncActions(Integer funcId) {
        EasyUIDataGrid g = new EasyUIDataGrid();
        List<Action> list = functionService.getFuncActions(funcId);
        g.setRows(list);
        return g;
    }

    @RequestMapping("/saveAction")
    @ResponseBody
    public Result<String> saveAction(String aPlayFlag, String fId, Integer aFuncId, String actionUrl, String includeType) {
        Action action = new Action();
        if (aPlayFlag.equals("1")) {
            action.setfId(0);
        } else {
            action.setfId(Integer.valueOf(fId));
        }
        action.setFuncId(aFuncId);
        action.setActionUrl(actionUrl);
        action.setIncludeType(includeType);
        Result<String> r = functionService.saveAction(aPlayFlag, action);
        return r;
    }
}
