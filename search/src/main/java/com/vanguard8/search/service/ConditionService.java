package com.vanguard8.search.service;

import com.vanguard8.search.entity.Condition;

import java.util.List;

public interface ConditionService {

    List<Condition> selectByModelId(Integer modelId);
}
