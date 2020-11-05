package com.vanguard8.search.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vanguard8.common.EasyUIDataGrid;
import com.vanguard8.common.Result;
import com.vanguard8.common.ResultGenerator;
import com.vanguard8.search.dao.ConditionDao;
import com.vanguard8.search.dao.FieldDao;
import com.vanguard8.search.dao.ModelDao;
import com.vanguard8.search.entity.Condition;
import com.vanguard8.search.entity.Field;
import com.vanguard8.search.entity.SrhModel;
import com.vanguard8.search.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class ModelServiceImpl implements ModelService {

    private Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);

    @Autowired
    private ModelDao modelDao;

    @Autowired
    private ConditionDao conditionDao;

    @Autowired
    private FieldDao fieldDao;

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
    public Result<String> saveModel(String playFlag, Integer oModelId, SrhModel model) {
        Result<String> r = null;
        Integer i = 0;
        try {
            if (playFlag.equals("1")) {
                i = modelDao.insert(model);
            } else if (playFlag.equals("2")) {
                i = modelDao.updateByPrimaryKey(oModelId, model);
                if (oModelId != model.getModelId()) {
                    fieldDao.updateFieldModelId(model.getModelId(), oModelId);
                    conditionDao.updateConditionModelId(model.getModelId(), oModelId);
                }
            } else if (playFlag.equals("3")) {
                //查询模板下的各种明细数据也都要删除掉
                //删除查询条件
                //删除展示字段
                i = modelDao.deleteByPrimaryKey(model.getModelId());
                if (i == 1) {
                    conditionDao.deleteByModelId(model.getModelId());
                    fieldDao.deleteByModelId(model.getModelId());
                }
            }
        } catch (DataAccessException e) {
            r = ResultGenerator.genFailResult("保存数据失败，可能是主键重复或输入内容有误！");
            return r;
        }
        if (i == 1) {
            r = ResultGenerator.genSuccessResult();
        } else {
            r = ResultGenerator.genFailResult("操作查询模板数据错误！");
        }
        return r;
    }

    /*
     * sqlStr:待处理的语句
     * conditions:参数列表
     * params:参数值Map
     * */
    private String replaceConditions(String sqlStr, List<Condition> conditions, HashMap<String, String> params) {
        for (int i = 0; i < conditions.size(); i++) {
            Condition condition = conditions.get(i);
            String paramValue = params.get(condition.getConditionId().toString());
            sqlStr = sqlStr.replace(condition.getConditionCode(), paramValue);
            logger.debug(sqlStr);
        }
        return sqlStr;
    }

    /**
     * 数字校验（正、负、小数）
     *
     * @param s
     * @return
     */
    private boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^-?\\d+(\\.\\d+)?$");
        else
            return false;
    }

    /**
     * List排序
     *
     * @param resultList Map数组
     * @param sortName   排序列名
     * @param orderName  是否按字符串排序，否则按数字排序
     * @throws Exception
     */
    private void listSort(List<Map<String, Object>> resultList, final String sortName, final String orderName) throws Exception {
        // 返回的结果集
        Collections.sort(resultList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                Object obj1 = o1.get(sortName);
                Object obj2 = o2.get(sortName);
                Boolean isNumber = false;
                Boolean isAsc = orderName.toLowerCase().equals("asc");
                if (obj1 instanceof Integer || obj1 instanceof BigInteger || obj1 instanceof Long
                        || obj1 instanceof Float || obj1 instanceof Double || obj1 instanceof BigDecimal) {
                    isNumber = true;
                }

                String s1 = obj1.toString();
                String s2 = obj2.toString();

                if (isNumber) {
                    if (s1.equals(""))
                        return isAsc ? -1 : 1;
                    if (s2.equals(""))
                        return isAsc ? 1 : -1;
                    if (!isNumeric(s1) && !isNumeric(s2))
                        return s1.compareTo(s2) * (isAsc ? 1 : -1);

                    if (!isNumeric(s1))
                        return isAsc ? -1 : 1;
                    if (!isNumeric(s2))
                        return isAsc ? 1 : -1;
                    return new BigDecimal(s1).compareTo(new BigDecimal(s2)) * (isAsc ? 1 : -1);
                } else {
                    return s1.compareTo(s2) * (isAsc ? 1 : -1);
                }
            }
        });
    }

    @Override
    public EasyUIDataGrid executeSelect(Integer page, Integer rows, String sort, String order, Integer modelId, HashMap<String, String> params) {
        EasyUIDataGrid grid = new EasyUIDataGrid();
        SrhModel model = modelDao.selectByPrimaryKey(modelId);
        List<Map<String, Object>> data;
        //对于没有排序字段和排序方式的，先设置默认值
        if (sort.equals("")) {
            sort = model.getSortField();
        }
        if (order.equals("")) {
            order = "asc";
        }

        //sql语句查询方式：先拼接sql语句
        //如果分页，则通过PageHelper进行分页处理，只取分页部分的数据
        //如果不分页，则通过executeSelectAll取全部数据
        if (model.getSqlTypeId() == 0) {
            //完整的sql语句：select+显示字段语句+表关联语句，然后再进行参数替换
            String totalSql;
            String sqlStr = model.getSqlStr();
            List<Condition> conditions = conditionDao.selectByModelId(modelId);
            sqlStr = replaceConditions(sqlStr, conditions, params);
            totalSql = "select ".concat(model.getFieldStr()).concat(" ").concat(sqlStr);
            long totalCount = 0;
            if (model.getPagination()) {
                String orderby = sort.concat(" ").concat(order).trim();
                PageHelper.startPage(page, rows, orderby);
                data = modelDao.executeSelect(totalSql);
                PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(data);
                totalCount = pageInfo.getTotal();
                grid.setTotal(totalCount);
                grid.setRows(data);
            } else {
                data = modelDao.executeSelect(totalSql);
                grid.setTotal(data.size());
                grid.setRows(data);
            }
            //页脚部分footer
            if (model.getShowFooter()) {
                Map<String, Object> footer = new HashMap<String, Object>();
                List<Field> fields = fieldDao.selectByModelId(modelId);
                String footerSql;
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    if (field.getShowFooter()) {
                        switch (field.getFooterTypeId()) {
                            //1静态文本
                            //2Count
                            //3Sum
                            //4Sum(字段1)/Sum(字段2),footerStr中格式为 "字段1,字段2"
                            //5Sum(字段1)/Sum(字段2)百分比格式
                            case 1:
                                footer.put(field.getFieldCode(), field.getFooterStr());
                                break;
                            case 2:
                                footer.put(field.getFieldCode(), totalCount);
                                break;
                            case 3:
                                footerSql = "select sum(".concat(field.getFooterStr()).concat(") ").concat(sqlStr);
                                BigDecimal r = modelDao.executeCalc(footerSql);
                                footer.put(field.getFieldCode(), r);
                                break;
                            default:
                                String[] footerFields = field.getFooterStr().split(",");
                                BigDecimal result = new BigDecimal("0");
                                if (footerFields.length == 2) {
                                    footerSql = "select if(sum(".concat(footerFields[0])
                                            .concat(")<>0,if(sum(")
                                            .concat(footerFields[1])
                                            .concat(")<>0,sum(")
                                            .concat(footerFields[0])
                                            .concat(")/sum(")
                                            .concat(footerFields[1])
                                            .concat("),100.00),0.00) ")
                                            .concat(sqlStr);
                                    result = modelDao.executeCalc(footerSql);
                                }
                                result = result.setScale(4, BigDecimal.ROUND_HALF_UP);
                                if (field.getFooterTypeId() == 4) {
                                    footer.put(field.getFieldCode(), result.setScale(2));
                                } else if (field.getFooterTypeId() == 5) {
                                    footer.put(field.getFieldCode(), result.multiply(new BigDecimal("100")).setScale(2).toString().concat("%"));
                                }
                        }
                    }
                }
                List<Map<String, Object>> footers = new ArrayList<>();
                footers.add(footer);
                grid.setFooter(footers);
            }
        } else if (model.getSqlTypeId() == 1) {
            //调用存储过程查询
            //分页不分页都是先把所有数据查出来，排好序，需要分页则获取所需的部分数据
            String sqlStr = model.getProcStr();
            List<Condition> conditions = conditionDao.selectByModelId(modelId);
            sqlStr = replaceConditions(sqlStr, conditions, params);
            data = modelDao.executeSelectByProc(sqlStr);
            grid.setTotal(data.size());

            //开始对data进行排序
            try {
                listSort(data, sort, order);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            //页脚部分footer
            //把需要显示Footer的逐一处理
            if (model.getShowFooter()) {
                Map<String, Object> footer = new HashMap<String, Object>();
                List<Field> fields = fieldDao.selectByModelId(modelId);
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    if (field.getShowFooter()) {
                        switch (field.getFooterTypeId()) {
                            //1静态文本
                            //2Count
                            //3Sum
                            //4Sum(字段1)/Sum(字段2),footerStr中格式为 "字段1,字段2"
                            //5Sum(字段1)/Sum(字段2)百分比格式
                            case 1:
                                footer.put(field.getFieldCode(), field.getFooterStr());
                                break;
                            case 2:
                                footer.put(field.getFieldCode(), data.size());
                                break;
                            case 3:
                                BigDecimal sum = new BigDecimal("0");
                                for (int j = 0; j < data.size(); j++) {
                                    sum = sum.add(new BigDecimal(data.get(j).get(field.getFieldCode()).toString()));
                                }
                                footer.put(field.getFieldCode(), sum);
                                break;
                            default:
                                String[] footerFields = field.getFooterStr().split(",");
                                BigDecimal r = new BigDecimal("0");
                                if (footerFields.length == 2) {
                                    BigDecimal sum1 = new BigDecimal("0");
                                    BigDecimal sum2 = new BigDecimal("0");
                                    for (int j = 0; j < data.size(); j++) {
                                        sum1 = sum1.add(new BigDecimal(data.get(j).get(footerFields[0]).toString()));
                                        sum2 = sum2.add(new BigDecimal(data.get(j).get(footerFields[1]).toString()));
                                    }
                                    //不能除以0
                                    if (sum2.equals(BigDecimal.ZERO)) {
                                        r = new BigDecimal("100.00");
                                    } else {
                                        r = sum1.divide(sum2, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2);
                                    }
                                }
                                if (field.getFooterTypeId() == 4) {
                                    footer.put(field.getFieldCode(), r);
                                } else if (field.getFooterTypeId() == 5) {
                                    footer.put(field.getFieldCode(), r.toString().concat("%"));
                                }
                        }
                    }
                }
                List<Map<String, Object>> footers = new ArrayList<>();
                footers.add(footer);
                grid.setFooter(footers);
            }
            //-------------------------------------------
            if (model.getPagination()) {
                //如果分页，按页数、每页条数找到需要的那一页的数据返回
                int totalCount = data.size();
                int startIndex = (page - 1) * rows;
                List<Map<String, Object>> pageData = new ArrayList<Map<String, Object>>();
                for (int i = startIndex; i < startIndex + rows; i++) {
                    if (i >= totalCount) {
                        break;
                    }
                    pageData.add(data.get(i));
                }

                grid.setRows(pageData);
            } else {
                //如果不分页，直接返回
                grid.setRows(data);
            }
        }
        return grid;
    }

    @Override
    public List<Map<String, Object>> executeSelectAll(Integer modelId, HashMap<String, String> params) {
        SrhModel model = modelDao.selectByPrimaryKey(modelId);
        List<Map<String, Object>> data = null;
        String sqlStr = "select ".concat(model.getFieldStr()).concat(" ").concat(model.getSqlStr());
        List<Condition> conditions = conditionDao.selectByModelId(modelId);
        for (int i = 0; i < conditions.size(); i++) {
            Condition condition = conditions.get(i);
            String paramValue = params.get(condition.getConditionId().toString());
            sqlStr = sqlStr.replace(condition.getConditionCode(), paramValue);
            logger.debug(sqlStr);
        }
        String sort = model.getSortField();
        String orderby = sort.concat(" ").trim();
        data = modelDao.executeSelect(sqlStr);

        return data;
    }

    @Override
    public Integer executeCount(Integer modelId, HashMap<String, String> params) {
        return null;
    }
}
