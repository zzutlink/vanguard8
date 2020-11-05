package com.vanguard8.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vanguard8.base.dao.BaseDao;
import com.vanguard8.base.entity.BaseDetail;
import com.vanguard8.base.entity.BaseMain;
import com.vanguard8.base.service.BaseService;
import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public Result<String> saveBaseMain(String playFlag, Integer oBsId, BaseMain main) {
        Result<String> r = null;
        Integer i = 0;
        if (playFlag.equals("1")) {
            i = baseDao.insertBaseMain(main);
        } else if (playFlag.equals("2")) {
            i = baseDao.updateBaseMain(oBsId, main);
            if(main.getBsId()!=oBsId) {
                baseDao.updateDetailBsId(main.getBsId(), oBsId);
            }
        } else if (playFlag.equals("3")) {
            Integer bsId = main.getBsId();
            baseDao.deleteBaseDetail(bsId);
            i = baseDao.deleteBaseMain(bsId);
        }

        if (i == 1) {
            r = ResultGenerator.genSuccessResult();
        } else {
            r = ResultGenerator.genFailResult("操作基础模板数据错误！");
        }
        return r;
    }

    public EasyUIDataGrid getAllBaseMain(Integer page, Integer rows, String sort, String order, BaseMain main) {
        String orderby = sort.concat(" ").concat(order).trim();
        PageHelper.startPage(page, rows, orderby);
        List<BaseMain> list = baseDao.getAllBaseMain(main);
        EasyUIDataGrid grid = new EasyUIDataGrid();
        PageInfo<BaseMain> pageInfo = new PageInfo<>(list);
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(list);
        return grid;
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

    public Result<String> save(Integer bsId, String playFlag, String keyValue, HashMap<String, String> maps) {
        BaseMain base = baseDao.getBaseMain(bsId);
        Result<String> r = new Result<String>();
        //新增一条基础资料
        //先获取主键的值，然后写入
        //表单的参数名是一个组合字符串 例如 0#1#userName，split("#")之后第一个为fieldType,第二个repeatFlag,第三个fieldCode
        String sql = "";
        String fieldStr = "";
        String tmpStr = "";
        String valueStr = "";
        String value = "";
        String maxId = "";
        if (playFlag.equals("1")) {
            //先看是否需要生成主键值,1需要产生主键值
            if (base.getKeyFieldAutoCreate() == 1) {
                sql = "select " + base.getKeyField() + " from " + base.getTableName() + " order by " + base.getKeyField() + " desc limit 1";
                maxId = baseDao.selectMaxId(sql);
                //如果fieldLength==0则是整形，加1生成流水码，否则是字符串形式产生流水码
                if (base.getKeyFieldLength() == 0) {
                    if (maxId == null) {
                        maxId = "1";
                    } else {
                        maxId = String.valueOf(Integer.valueOf(maxId) + 1);
                    }
                } else {
                    int iTmp = (int) Math.pow(10, base.getKeyFieldLength());
                    if (maxId == null) {
                        maxId = "0";
                    }
                    maxId = String.valueOf(iTmp + 1 + Integer.valueOf(maxId)).substring(1);
                }
            }
            for (Map.Entry<String, String> map : maps.entrySet()) {
                tmpStr = map.getKey();
                String[] s = tmpStr.split("#");
                if (s[3].equals("0")) {
                    continue;
                }
                if (fieldStr.length() != 0) {
                    fieldStr = fieldStr + ",";
                    valueStr = valueStr + ",";
                }
                value = map.getValue();

                //检查是否是不允许重复的字段，若是，则到表中查找是否重复
                String tmpValue = "";
                if (s[1].equals("0")) {
                    if (!s[0].equals("0")) {
                        tmpValue = "'".concat(value).concat("'");
                    }
                    sql = "select count(*) from " + base.getTableName() + " where " + s[2] + "=" + tmpValue;
                    Integer i = baseDao.executeCount(sql);
                    if (i > 0) {
                        r = ResultGenerator.genFailResult(tmpValue + "出现重复！");
                        return r;
                    }
                }

                fieldStr += s[2];
                if (base.getKeyFieldAutoCreate() == 1 && s[2].equals(base.getKeyField())) {
                    value = maxId;
                }
                if (s[0].equals("0")) {
                    valueStr += value;
                } else {
                    //先把单引号替换成两个单引号，否则将导致sql语句格式不正确
                    value = value.replaceAll("'", "''");
                    valueStr += "'".concat(value).concat("'");
                }
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
        } else if (playFlag.equals("2")) {  //编辑 update tbname set xxxx where keyfield=xxxx
            if (base.getKeyFieldLength() > 0) {
                keyValue = keyValue.replaceAll("'", "''");
                keyValue = "'".concat(keyValue).concat("'");
            }

            for (Map.Entry<String, String> map : maps.entrySet()) {
                tmpStr = map.getKey();
                String[] s = tmpStr.split("#");

                //如果是不需要写表的字段则放弃
                if (s[3].equals("0")) {
                    continue;
                }

                //如果当前fieldStr有其他值，则加上一个,逗号
                if (fieldStr.length() != 0) {
                    fieldStr = fieldStr + ",";
                }

                value = map.getValue();
                if (!s[0].equals("0")) {
                    //先把单引号替换成两个单引号，否则将导致sql语句格式不正确
                    value = value.replaceAll("'", "''");
                    value = "'".concat(value).concat("'");
                }

                //检查是否是不允许重复的字段，若是，则到表中查找是否重复
                if (!s[1].equals("1")) {
                    sql = "select count(*) from " + base.getTableName() + " where " + base.getKeyField() + "<>" + keyValue + " and " + s[2] + "=" + value;
                    Integer i = baseDao.executeCount(sql);
                    if (i > 0) {
                        r = ResultGenerator.genFailResult(value + "出现重复！");
                        return r;
                    }
                }
                fieldStr += s[2].concat("=").concat(value);
            }
            valueStr = base.getKeyField().concat("=").concat(keyValue);
            sql = "update " + base.getTableName() + " set " + fieldStr + " where " + valueStr;
            try {
                Integer i = baseDao.executeUpdate(sql);
                if (i == 1) {
                    r = ResultGenerator.genSuccessResult();
                } else {
                    r = ResultGenerator.genFailResult("编辑失败！");
                }
            } catch (Exception e) {
                r = ResultGenerator.genFailResult("编辑失败！");
            }
        } else if (playFlag.equals("3")) {
            if (base.getKeyFieldLength() > 0) {
                keyValue = keyValue.replaceAll("'", "''");
                keyValue = "'".concat(keyValue).concat("'");
            }

            //检查是否已经使用，已经使用则不允许删除
            //select count(*) from use_tbname where fieldname=value

            if (!base.getCheckUseTable().equals("")) {
                sql = "select count(*) from " + base.getCheckUseTable() + " where " + base.getCheckUseField() + "=" + keyValue;
                Integer existCount = baseDao.executeCount(sql);
                if (existCount > 0) {  //该数据已经使用，不允许删除
                    r = ResultGenerator.genFailResult("该数据已经使用，无法删除！");
                    return r;
                }
            }
            valueStr = base.getKeyField().concat("=").concat(keyValue);
            sql = "delete from " + base.getTableName() + " where " + valueStr;
            try {
                Integer i = baseDao.executeDelete(sql);
                if (i == 1) {
                    r = ResultGenerator.genSuccessResult();
                } else {
                    r = ResultGenerator.genFailResult("删除失败！");
                }
            } catch (Exception e) {
                r = ResultGenerator.genFailResult("删除失败！");
            }
        }
        logger.debug(sql);
        return r;
    }

    @Override
    public Result<String> saveBaseDetail(Integer playFlag, BaseDetail detail) {
        Result<String> r = null;
        Integer i = 0;
        if (playFlag == 1) {
            i = baseDao.insertBaseDetail(detail);
        } else if (playFlag == 2) {
            i = baseDao.updateBaseDetail(detail);
        } else if (playFlag == 3) {
            i = baseDao.deleteBaseDetailById(detail.getDetailId());
        }
        if (i == 1) {
            r = ResultGenerator.genSuccessResult();
        } else {
            r = ResultGenerator.genFailResult("保存数据失败！");
        }
        return r;
    }

    @Override
    public Integer executeInsert(String sql) {
        return baseDao.executeInsert(sql);
    }
}
