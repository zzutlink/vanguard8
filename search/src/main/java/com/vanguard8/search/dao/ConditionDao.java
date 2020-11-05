package com.vanguard8.search.dao;

import com.vanguard8.search.entity.Condition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConditionDao {
    int deleteByPrimaryKey(Integer conditionId);

    int insert(Condition record);

    Condition selectByPrimaryKey(Integer conditionId);

    int updateByPrimaryKeySelective(Condition record);

    int updateByPrimaryKey(Condition record);

    List<Condition> selectByModelId(Integer modelId);

    int updateConditionModelId(@Param("nModelId") Integer nModelId,@Param("modelId") Integer modelId);

    int deleteByModelId(Integer modelId);
}