package com.vanguard8.search.dao;

import com.vanguard8.search.entity.SrhModel;

import java.util.List;
import java.util.Map;

public interface ModelDao {
    int deleteByPrimaryKey(Integer modelId);

    int insert(SrhModel record);

    SrhModel selectByPrimaryKey(Integer modelId);

    SrhModel selectModelForClient(Integer modelId);

    List<SrhModel> selectModelList(String modelName);

    int updateByPrimaryKeySelective(SrhModel record);

    int updateByPrimaryKey(SrhModel record);

    List<Map<String,Object>> executeSelect(String sql);

    Integer executeCount(String sql);
}