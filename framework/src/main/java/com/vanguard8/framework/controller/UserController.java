package com.vanguard8.framework.controller;

import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.common.SessionName;
import com.vanguard8.framework.entity.Dept;
import com.vanguard8.framework.entity.Deptsta;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.entity.UserFlag;
import com.vanguard8.framework.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        return "/core/changePwd";
    }

    @RequestMapping("/user")
    public String user() {
        return "/core/user";
    }

    @RequestMapping("/submitChange")
    @ResponseBody
    public Result<String> submitChange(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword) {
        Result<String> r = null;
        if (newPassword.equals(confirmPassword)) {
            User user = (User) request.getSession().getAttribute(SessionName.USER);
            r = userService.changePwd(user.getUserId(), oldPassword, newPassword);
        } else {
            r = ResultGenerator.genFailResult("新密码与确认密码不匹配，请重新输入！");
        }
        return r;
    }

    @RequestMapping("/getUsers")
    @ResponseBody
    public EasyUIDataGrid getUsers(Integer page, Integer rows, @RequestParam(defaultValue = "") String sort,
                                   @RequestParam(defaultValue = "") String order,
                                   @RequestParam(defaultValue = "") String UserName,
                                   @RequestParam(defaultValue = "") String LoginName) {
        User user = new User();
        user.setUserName(UserName);
        user.setLoginName(LoginName);
        return userService.getUsers(page, rows, sort, order, user);
    }

    @RequestMapping("/saveUser")
    @ResponseBody
    public Result<String> saveUser(HttpServletRequest request, String PlayFlag, String userId, Integer DSID, String userName,
                                   String loginName, String Passwd,@RequestParam(defaultValue = "0") String PwdFlag, String WorkNO,
                                   Integer UseFlag, @RequestParam(defaultValue = "") String BussDeptID) {
        User user = new User();
        user.setUserId(0);
        if(!userId.equals("自动生成")){
            user.setUserId(Integer.parseInt(userId));
        }
        user.setUserName(userName);
        user.setLoginName(loginName);
        user.setSalt("");
        user.setPassword(Passwd);

        Deptsta d = new Deptsta();
        d.setDsId(DSID);
        user.setDeptsta(d);

        UserFlag uf = new UserFlag();
        uf.setFlagId(UseFlag);
        user.setUserFlag(uf);

        user.setWorkNo(WorkNO);

        Dept dp = new Dept();
        dp.setDeptId(BussDeptID);
        user.setDept(dp);

        //playflag,user,pwdflag参数进行接下来传递
        Result<String> r;

        try {
            User sessionUser = (User) request.getSession().getAttribute(SessionName.USER);
            r = userService.saveUser(PlayFlag, user, PwdFlag, sessionUser);
        }catch(DataAccessException e){
            logger.error(e.getMessage());
            r = ResultGenerator.genFailResult("写入数据库时出现异常！");
        }
        return r;
    }
}
