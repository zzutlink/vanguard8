package com.vanguard8.framework.service;

import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;
import com.vanguard8.framework.entity.User;
import org.springframework.dao.DataAccessException;

public interface UserService {
    Result<User> login(String loginName, String password);

    Result<String> changePwd(Integer userId, String oldPassword, String newPassword);

    EasyUIDataGrid getUsers(Integer page, Integer rows, String sort, String order, User user);

    Result<String> saveUser(String playFlag, User user, String pwdFlag, User sessionUser) throws DataAccessException;
}
