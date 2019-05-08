package com.vanguard8.framework.service;

import com.vanguard8.framework.vo.Dict;
import com.vanguard8.framework.vo.DictCondition;

import java.util.List;

public interface DictService {
    public List<Dict> getDict(DictCondition condition);
}
