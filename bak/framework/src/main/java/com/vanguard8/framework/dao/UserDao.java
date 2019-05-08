package com.vanguard8.framework.dao;

import com.vanguard8.framework.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByLoginname(String loginName);

    List<User> selectByDsId(Integer dsId);

    List<User> selectUsers(User user);

    int checkLoginName(@Param("loginName") String loginName, @Param("userId") Integer userId);

    int checkWorkNo(@Param("workNo") String workNo, @Param("userId") Integer userId);

    int insertUserDept(@Param("userId") Integer userId, @Param("deptId") String deptId);

    int deleteUserDept(Integer userId);
}