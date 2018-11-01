package com.vanguard8.framework.service.impl;

import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.framework.dao.UserDao;
import com.vanguard8.framework.entity.User;
import com.vanguard8.framework.service.UserService;
import com.vanguard8.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
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

    @Override
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
}
