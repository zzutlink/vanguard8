package com.vanguard8.search.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class Condition implements Serializable {
    private Integer conditionId;

    private Integer modelId;

    private Integer orderValue;

    private String conditionCode;

    private String conditionName;

    private Integer showWidth;

    private Byte fieldTypeId;

    private String fieldType;

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    private String initValue;

    private Boolean mustFlag;

    private Boolean refFlag;

    private String refString;

    private String refIdStr;

    private String refTextStr;

    private static final long serialVersionUID = 1L;

    public Integer getConditionId() {
        return conditionId;
    }

    public void setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    public String getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(String conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public Integer getShowWidth() {
        return showWidth;
    }

    public void setShowWidth(Integer showWidth) {
        this.showWidth = showWidth;
    }

    public Byte getFieldTypeId() {
        return fieldTypeId;
    }

    public void setFieldTypeId(Byte fieldTypeId) {
        this.fieldTypeId = fieldTypeId;
    }

    public String getInitValue() {
        return initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

    public Boolean getMustFlag() {
        return mustFlag;
    }

    public void setMustFlag(Boolean mustFlag) {
        this.mustFlag = mustFlag;
    }

    public Boolean getRefFlag() {
        return refFlag;
    }

    public void setRefFlag(Boolean refFlag) {
        this.refFlag = refFlag;
    }

    public String getRefString() {
        return refString;
    }

    public void setRefString(String refString) {
        this.refString = refString;
    }

    public String getRefIdStr() {
        return refIdStr;
    }

    public void setRefIdStr(String refIdStr) {
        this.refIdStr = refIdStr;
    }

    public String getRefTextStr() {
        return refTextStr;
    }

    public void setRefTextStr(String refTextStr) {
        this.refTextStr = refTextStr;
    }
}