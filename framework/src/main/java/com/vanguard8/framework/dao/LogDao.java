package com.vanguard8.framework.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

public interface LogDao {
    Integer saveLog(@Param("userId") Integer userId, @Param("userName") String userName, @Param("logType") String logType, @Param("content") String content) throws DataAccessException;
}
