package com.vanguard8.search.dao;

import com.vanguard8.search.entity.Model;

public interface ModelDao {
    int deleteByPrimaryKey(Integer modelId);

    int insert(Model record);

    int insertSelective(Model record);

    Model selectByPrimaryKey(Integer modelId);

    int updateByPrimaryKeySelective(Model record);

    int updateByPrimaryKey(Model record);
}