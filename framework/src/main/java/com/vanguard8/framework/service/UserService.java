package com.vanguard8.framework.service;

import com.vanguard8.common.Result;
import com.vanguard8.framework.entity.User;

public interface UserService {
    Result<User> login(String loginName, String password);

    Result<String> changePwd(Integer userId, String oldPassword, String newPassword);
}
