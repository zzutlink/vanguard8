package com.vanguard8.framework.service.impl;

import com.vanguard8.framework.dao.FunctionDao;
import com.vanguard8.framework.entity.Function;
import com.vanguard8.framework.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    private FunctionDao functionDao;

    @Override
    public List<Function> getFunctions(Integer dsId) {
        List<Function> functions = functionDao.selectFunctions(dsId);
        return functions;
    }
}
