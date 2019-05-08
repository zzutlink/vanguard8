package com.vanguard8.search.service.impl;

import com.vanguard8.search.dao.FieldDao;
import com.vanguard8.search.entity.Field;
import com.vanguard8.search.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldDao fieldDao;

    public List<Field> selectByModelId(Integer modelId) {
        return fieldDao.selectByModelId(modelId);
    }
}
