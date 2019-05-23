package com.vanguard8.search.service.impl;

import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
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

    @Override
    public Result<String> saveField(String playFlag, Field field) {
        Result<String> r = null;
        Integer i = 0;
        if (playFlag.equals("1")) {
            i = fieldDao.insert(field);
        } else if (playFlag.equals("2")) {
            i = fieldDao.updateByPrimaryKey(field);
        } else if (playFlag.equals("3")) {
            i = fieldDao.deleteByPrimaryKey(field.getFieldId());
        }

        if (i == 1) {
            r = ResultGenerator.genSuccessResult();
        } else {
            r = ResultGenerator.genFailResult("操作查询模板显示字段错误！");
        }
        return r;
    }
}
