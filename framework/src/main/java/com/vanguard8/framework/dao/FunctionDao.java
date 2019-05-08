package com.vanguard8.framework.dao;


import com.vanguard8.framework.entity.Function;
import com.vanguard8.framework.entity.StaFunction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FunctionDao {
    int deleteByPrimaryKey(Integer funcId);

    int deleteStatFuncByFuncId(Integer funcId);

    int insertFunction(Function function);

    int updateByPrimaryKey(Function function);

    Function selectFunction(Integer funcId);

    List<Function> selectFunctions(Integer dsId);

    int insertStatFunc(StaFunction record);

    int deleteStatFunc(Integer dsId);

    List<Function> selectFuncsWithRight(Integer dsId);

    List<Function> selectLevelFunctions(Integer funcId);

    String selectMaxCode(String funcCode);
}