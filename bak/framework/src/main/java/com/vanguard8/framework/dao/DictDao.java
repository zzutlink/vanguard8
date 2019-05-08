package com.vanguard8.framework.dao;

import com.vanguard8.framework.vo.Dict;
import com.vanguard8.framework.vo.DictCondition;

import java.util.List;

public interface DictDao {
    public List<Dict> getDict(DictCondition condition);
}
