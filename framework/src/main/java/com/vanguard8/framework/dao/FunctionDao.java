package com.vanguard8.framework.dao;

import com.vanguard8.framework.entity.Function;

import java.util.List;

public interface FunctionDao {
    int deleteByPrimaryKey(String funcId);

    int insert(Function record);

    int insertSelective(Function record);

    Function selectByPrimaryKey(String funcId);

    int updateByPrimaryKeySelective(Function record);

    int updateByPrimaryKey(Function record);

    List<Function> selectFunctions(Integer dsId);
}