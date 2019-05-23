package com.vanguard8.search.dao;

import com.vanguard8.search.entity.Field;

import java.util.List;

public interface FieldDao {
    int deleteByPrimaryKey(Integer fieldId);

    int insert(Field record);

    Field selectByPrimaryKey(Integer fieldId);

    int updateByPrimaryKey(Field record);

    List<Field> selectByModelId(Integer modelId);
}