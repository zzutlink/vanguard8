package com.vanguard8.search.service;

import com.vanguard8.common.Result;
import com.vanguard8.search.entity.Field;

import java.util.List;

public interface FieldService {

    List<Field> selectByModelId(Integer modelId);

    Result<String> saveField(String playFlag, Field field);
}
