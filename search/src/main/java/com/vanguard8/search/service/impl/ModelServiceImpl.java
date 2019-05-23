package com.vanguard8.search.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.search.dao.ConditionDao;
import com.vanguard8.search.dao.ModelDao;
import com.vanguard8.search.entity.Condition;
import com.vanguard8.search.entity.SrhModel;
import com.vanguard8.search.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModelServiceImpl implements ModelService {

    private Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private ConditionDao conditionDao;

    public SrhModel getModelForClient(Integer modelId) {
        return modelDao.selectModelForClient(modelId);
    }

    @Override
    public EasyUIDataGrid selectModelList(Integer page, Integer rows, String sort, String order, String pModelName) {
        String orderby = sort.concat(" ").concat(order).trim();
        PageHelper.startPage(page, rows, orderby);
        List<SrhModel> list = modelDao.selectModelList(pModelName);
        EasyUIDataGrid grid = new EasyUIDataGrid();
        PageInfo<SrhModel> pageInfo = new PageInfo<>(list);
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(list);
        return grid;
    }

    @Override
    @Transactional
    public Result<String> saveModel(String playFlag, SrhModel model) {
        Result<String> r = null;
        Integer i = 0;
        if (playFlag.equals("1")) {
            i = modelDao.insert(model);
        } else if (playFlag.equals("2")) {
            i = modelDao.updateByPrimaryKey(model);
        } else if (playFlag.equals("3")) {
            //查询模板下的各种明细数据也都要删除掉
            //删除查询条件
            //删除展示字段
            i = modelDao.deleteByPrimaryKey(model.getModelId());
        }

        if (i == 1) {
            r = ResultGenerator.genSuccessResult();
        } else {
            r = ResultGenerator.genFailResult("操作查询模板数据错误！");
        }
        return r;
    }

    @Override
    public EasyUIDataGrid executeSelect(Integer page, Integer rows, String sort, String order, Integer modelId, HashMap<String, String> params) {
        EasyUIDataGrid grid = new EasyUIDataGrid();
        SrhModel model = modelDao.selectByPrimaryKey(modelId);
        if(model==null){
            grid.setTotal(0);
            grid.setRows(new ArrayList<>());
        }
        else{
            String sqlStr = model.getSqlStr();
            List<Condition> conditions = conditionDao.selectByModelId(modelId);
            for(int i=0;i<conditions.size();i++){
                Condition condition = conditions.get(i);
                String paramValue = params.get(condition.getConditionId().toString());
                sqlStr = sqlStr.replace(condition.getConditionCode(),paramValue);
                logger.debug(sqlStr);
            }
            String orderby = sort.concat(" ").concat(order).trim();
            PageHelper.startPage(page, rows, orderby);
            List<Map<String,Object>> data = modelDao.executeSelect(sqlStr);
            PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(data);
            grid.setTotal(pageInfo.getTotal());
            grid.setRows(data);
        }
        return grid;
    }

    @Override
    public Integer executeCount(Integer modelId, HashMap<String, String> params) {
        return null;
    }
}
