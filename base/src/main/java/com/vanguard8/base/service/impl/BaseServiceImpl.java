package com.vanguard8.base.service.impl;

import com.vanguard8.base.dao.BaseDao;
import com.vanguard8.base.entity.BaseDetail;
import com.vanguard8.base.entity.BaseMain;
import com.vanguard8.base.service.BaseService;
import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseServiceImpl implements BaseService {

    private Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private BaseDao baseDao;

    public BaseMain getBaseMain(Integer bsId) {
        return baseDao.getBaseMain(bsId);
    }

    public List<BaseDetail> getBaseDetail(Integer bsId) {
        return baseDao.getBaseDetail(bsId);
    }

    //处理拼接sql语句
    private String createSql(Integer bsId, boolean countFlag, String paramName, String paramValue) {
        BaseMain m = baseDao.getBaseMain(bsId);
        String tmp = "";
        if (countFlag) {
            tmp = "count(*)";
        } else {
            tmp = m.getFieldStr();
        }
        String sql = "select " + tmp + " from " + m.getTableStr() + " where 1=1 ";
        String conditionStr = m.getConditionStr();
        if (conditionStr != null && conditionStr.length() != 0) {
            sql += " and " + conditionStr;
        }
        if (paramName != null && paramName.length() != 0) {
            sql += " and " + paramName + " like '%" + paramValue + "%'";
        }
        return sql;
    }

    public List<Map<String, Object>> executeSelect(Integer page, Integer rows, String sort, String order, Integer bsId, String paramName, String paramValue) {
        String sql = createSql(bsId, false, paramName, paramValue);
        sql = PageUtil.pageSql(sql, page, rows, sort, order);
        logger.debug(sql);
        return baseDao.executeSelect(sql);
    }

    public Integer executeCount(Integer bsId, String paramName, String paramValue) {
        String sql = createSql(bsId, true, paramName, paramValue);
        logger.debug(sql);
        return baseDao.executeCount(sql);
    }

    public Result<String> save(Integer bsId, String playFlag, HashMap<String, String> maps) {
        BaseMain base = baseDao.getBaseMain(bsId);
        Result<String> r = new Result<String>();
        //新增一条基础资料
        //先获取主键的值，然后写入
        String sql = "";
        String fieldStr = "";
        String valueStr = "";
        if (playFlag.equals("1")) {
            for (Map.Entry<String, String> map : maps.entrySet()) {
                if (fieldStr.length() != 0) {
                    fieldStr = fieldStr + ",";
                    valueStr = valueStr + ",";
                }
                fieldStr += map.getKey();
                valueStr += map.getValue();
            }
            sql = "insert into " + base.getTableName() + "(" + fieldStr + ") values (" + valueStr + ")";
            try {
                Integer i = baseDao.executeInsert(sql);
                if (i == 1) {
                    r = ResultGenerator.genSuccessResult();
                } else {
                    r = ResultGenerator.genFailResult("新增失败！");
                }
            } catch (Exception e) {
                r = ResultGenerator.genFailResult("新增失败！");
            }
        }
        logger.debug(sql);
        return r;
    }
}
