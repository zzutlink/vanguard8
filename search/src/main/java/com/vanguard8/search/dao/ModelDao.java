package com.vanguard8.search.dao;

import com.vanguard8.search.entity.SrhModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ModelDao {
    int deleteByPrimaryKey(Integer modelId) throws DataAccessException;

    int insert(SrhModel record) throws DataAccessException;

    SrhModel selectByPrimaryKey(Integer modelId);

    SrhModel selectModelForClient(Integer modelId);

    List<SrhModel> selectModelList(String modelName);

    int updateByPrimaryKey(@Param("oModelId") Integer oModelId,@Param("model") SrhModel record) throws DataAccessException;

    List<Map<String,Object>> executeSelect(String sql);

    List<Map<String,Object>> executeSelectByProc(String procStr);

    BigDecimal executeCalc(String sql);
}