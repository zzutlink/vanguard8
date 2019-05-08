package com.vanguard8.base.entity;

import java.util.List;

public class BaseMain {
    private Integer bsId;
    private String bsName;
    private String fieldStr;
    private String tableStr;
    private String conditionStr;
    private String sortField;
    private String tableName;
    private String checkUseTable;
    private String checkUseField;
    private String keyField;
    private Integer keyFieldAutoCreate;
    private Integer keyFieldLength;
    private List<BaseDetail> baseDetails;

    public Integer getBsId() {
        return bsId;
    }

    public void setBsId(Integer bsId) {
        this.bsId = bsId;
    }

    public String getBsName() {
        return bsName;
    }

    public void setBsName(String bsName) {
        this.bsName = bsName;
    }

    public String getFieldStr() {
        return fieldStr;
    }

    public void setFieldStr(String fieldStr) {
        this.fieldStr = fieldStr;
    }

    public String getTableStr() {
        return tableStr;
    }

    public void setTableStr(String tableStr) {
        this.tableStr = tableStr;
    }

    public String getConditionStr() {
        return conditionStr;
    }

    public void setConditionStr(String conditionStr) {
        this.conditionStr = conditionStr;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCheckUseTable() {
        return checkUseTable;
    }

    public void setCheckUseTable(String checkUseTable) {
        this.checkUseTable = checkUseTable;
    }

    public String getCheckUseField() {
        return checkUseField;
    }

    public void setCheckUseField(String checkUseField) {
        this.checkUseField = checkUseField;
    }

    public String getKeyField() {
        return keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public Integer getKeyFieldLength() {
        return keyFieldLength;
    }

    public void setKeyFieldLength(Integer keyFieldLength) {
        this.keyFieldLength = keyFieldLength;
    }

    public List<BaseDetail> getBaseDetails() {
        return baseDetails;
    }

    public void setBaseDetails(List<BaseDetail> baseDetails) {
        this.baseDetails = baseDetails;
    }

    public Integer getKeyFieldAutoCreate() {
        return keyFieldAutoCreate;
    }

    public void setKeyFieldAutoCreate(Integer keyFieldAutoCreate) {
        this.keyFieldAutoCreate = keyFieldAutoCreate;
    }
}
