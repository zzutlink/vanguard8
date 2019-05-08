package com.vanguard8.framework.service.impl;

import com.vanguard8.framework.dao.DictDao;
import com.vanguard8.framework.service.DictService;
import com.vanguard8.framework.vo.Dict;
import com.vanguard8.framework.vo.DictCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictDao dictDao;

    @Override
    public List<Dict> getDict(DictCondition condition) {
        return dictDao.getDict(condition);
    }
}
