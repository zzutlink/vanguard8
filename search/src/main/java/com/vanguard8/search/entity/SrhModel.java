package com.vanguard8.search.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class SrhModel implements Serializable {
    private Integer modelId;

    private String modelName;

    private Integer sqlTypeId;

    private String sqlType;

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    private Boolean pagination;

    private String sqlStr;

    private String sortField;

    private Boolean exportFlag;

    private Boolean printFlag;

    private static final long serialVersionUID = 1L;

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getSqlTypeId() {
        return sqlTypeId;
    }

    public void setSqlTypeId(Integer sqlTypeId) {
        this.sqlTypeId = sqlTypeId;
    }

    public Boolean getPagination() {
        return pagination;
    }

    public void setPagination(Boolean pagination) {
        this.pagination = pagination;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Boolean getExportFlag() {
        return exportFlag;
    }

    public void setExportFlag(Boolean exportFlag) {
        this.exportFlag = exportFlag;
    }

    public Boolean getPrintFlag() {
        return printFlag;
    }

    public void setPrintFlag(Boolean printFlag) {
        this.printFlag = printFlag;
    }
}