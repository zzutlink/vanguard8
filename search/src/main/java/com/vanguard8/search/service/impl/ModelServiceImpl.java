package com.vanguard8.search.service.impl;

import com.vanguard8.search.dao.ModelDao;
import com.vanguard8.search.entity.Model;
import com.vanguard8.search.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelDao modelDao;

    public Model getModel(Integer modelId) {
        return modelDao.selectByPrimaryKey(modelId);
    }
}
