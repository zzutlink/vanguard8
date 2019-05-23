package com.vanguard8.search.service.impl;

import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
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

    @Override
    public Result<String> saveCondition(String playFlag, Condition condition) {
        Result<String> r = null;
        Integer i = 0;
        if (playFlag.equals("1")) {
            i = conditionDao.insert(condition);
        } else if (playFlag.equals("2")) {
            i = conditionDao.updateByPrimaryKey(condition);
        } else if (playFlag.equals("3")) {
            i = conditionDao.deleteByPrimaryKey(condition.getConditionId());
        }

        if (i == 1) {
            r = ResultGenerator.genSuccessResult();
        } else {
            r = ResultGenerator.genFailResult("操作查询模板条件错误！");
        }
        return r;
    }
}
