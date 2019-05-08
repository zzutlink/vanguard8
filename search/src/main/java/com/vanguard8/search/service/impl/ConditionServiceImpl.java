package com.vanguard8.search.service.impl;

import com.vanguard8.search.dao.ConditionDao;
import com.vanguard8.search.entity.Condition;
import com.vanguard8.search.service.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionServiceImpl implements ConditionService {

    @Autowired
    private ConditionDao conditionDao;

    public List<Condition> selectByModelId(Integer modelId) {
        return conditionDao.selectByModelId(modelId);
    }
}
