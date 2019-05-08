package com.vanguard8.framework.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.framework.dao.LogDao;
import com.vanguard8.framework.dao.UserDao;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.UserService;
import com.vanguard8.util.MD5Util;
import com.vanguard8.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private LogDao logDao;

    public Result<User> login(String loginName, String password) {
        Result<User> r;
        //根据登录名获取到对应用户信息
        User user = userDao.selectByLoginname(loginName);
        if (user == null) {
            r = ResultGenerator.genFailResult("登录账号错误！");
        } else {
            //输入的密码加盐后MD5，与数据库中比对
            String pwd = MD5Util.MD5Encode(password.concat(user.getSalt()));
            if (pwd.equals(user.getPassword())) {
                //去掉盐与密码再传递
                user.setSalt("");
                user.setPassword("");
                r = ResultGenerator.genSuccessResult(user);
            } else {
                r = ResultGenerator.genFailResult("登录密码错误！");
            }
        }
        return r;

    }

    public Result<String> changePwd(Integer userId, String oldPassword, String newPassword) {
        Result<String> r = new Result<String>();
        User user = userDao.selectByPrimaryKey(userId);
        if (user == null) {
            r = ResultGenerator.genFailResult("未找到用户信息！");
        } else {
            String pwd = MD5Util.MD5Encode(oldPassword.concat(user.getSalt()));
            if (pwd.equals(user.getPassword())) {
                User record = new User();
                record.setUserId(userId);
                record.setPassword(pwd);
                Integer i = userDao.updateByPrimaryKeySelective(record);
                if (i == 1) {
                    r = ResultGenerator.genSuccessResult();
                } else {
                    r = ResultGenerator.genFailResult("变更密码时出现错误！");
                }
            } else {
                r = ResultGenerator.genFailResult("原密码输入错误！");
            }
        }
        return r;
    }

    public EasyUIDataGrid getUsers(Integer page, Integer rows, String sort, String order, User user) {
        String orderby = sort.concat(" ").concat(order).trim();
        PageHelper.startPage(page, rows, orderby);
        List<User> users = userDao.selectUsers(user);
        EasyUIDataGrid grid = new EasyUIDataGrid();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(users);
        return grid;
    }

    @Transactional
    public Result<String> saveUser(String playFlag, User user, String pwdFlag, User sessionUser) throws DataAccessException {
        Result<String> r;
        //playFlag:0新增1修改2删除
        int i = userDao.checkLoginName(user.getLoginName(),user.getUserId());
        if(i>0){
            r=ResultGenerator.genFailResult("已存在相同登录名！");
            return r;
        }

        i = userDao.checkWorkNo(user.getWorkNo(),user.getUserId());
        if(i>0){
            r=ResultGenerator.genFailResult("已存在相同工号！");
            return r;
        }

        if(playFlag.equals("0")){

            //随机生成salt，生成加密后的密码
            String salt = StringUtil.generateString(6);
            String pwd = MD5Util.MD5Encode(user.getPassword().concat(salt));
            user.setSalt(salt);
            user.setPassword(pwd);

            i = userDao.insert(user);

            String deptId =user.getDept().getDeptId();
            if(!deptId.equals("")){
                userDao.insertUserDept(user.getUserId(), deptId);
            }
        } else if(playFlag.equals("1")){
            //编辑用户信息
            if(pwdFlag.equals("1")){
            //随机生成salt，生成加密后的密码
            String salt = StringUtil.generateString(6);
            String pwd = MD5Util.MD5Encode(user.getPassword().concat(salt));
            user.setSalt(salt);
            user.setPassword(pwd);
            }

            i = userDao.updateByPrimaryKey(user);

            userDao.deleteUserDept(user.getUserId());
            String deptId =user.getDept().getDeptId();
            if(!deptId.equals("")){
                userDao.insertUserDept(user.getUserId(), deptId);
            }
        } else {
            r = ResultGenerator.genFailResult("参数出现异常！");
        }

        logDao.saveLog(sessionUser.getUserId(),sessionUser.getUserName(),"sys_user:".concat(playFlag).concat(":").concat(pwdFlag), user.toString());
        r = ResultGenerator.genSuccessResult();
        return r;
    }
}
