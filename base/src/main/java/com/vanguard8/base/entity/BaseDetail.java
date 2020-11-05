package com.vanguard8.base.entity;

import java.util.List;

public class BaseDetail {
    private Integer detailId;
    private Integer bsId;
    private String fieldCode;
    private String fieldName;
    private Integer fieldType;
    private String fieldTypeName;
    private Integer controlTypeId;
    private String controlType;
    private Integer showWidth;
    private Integer showHeight;
    private Integer showFlag;
    private Integer searchFlag;
    private Integer editFlag;
    private Integer chainFlag;
    private Integer chainDetailId;
    private String chainFieldName;
    private String refString;
    private String refIdStr;
    private String refTextStr;
    private Integer readonlyFlag;
    private Integer repeatFlag;
    private Integer nullFlag;
    private String defaultValue;
    private Integer orderValue;
    private Integer saveFlag;
    private List<BaseDetail> comboboxList;

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getBsId() {
        return bsId;
    }

    public void setBsId(Integer bsId) {
        this.bsId = bsId;
    }

    public String getFieldTypeName() {
        return fieldTypeName;
    }

    public void setFieldTypeName(String fieldTypeName) {
        this.fieldTypeName = fieldTypeName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(Integer showFlag) {
        this.showFlag = showFlag;
    }

    public Integer getShowWidth() {
        return showWidth;
    }

    public void setShowWidth(Integer showWidth) {
        this.showWidth = showWidth;
    }

    public Integer getShowHeight() {
        return showHeight;
    }

    public void setShowHeight(Integer showHeight) {
        this.showHeight = showHeight;
    }

    public Integer getSearchFlag() {
        return searchFlag;
    }

    public void setSearchFlag(Integer searchFlag) {
        this.searchFlag = searchFlag;
    }

    public Integer getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(Integer editFlag) {
        this.editFlag = editFlag;
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

    public Integer getReadonlyFlag() {
        return readonlyFlag;
    }

    public void setReadonlyFlag(Integer readonlyFlag) {
        this.readonlyFlag = readonlyFlag;
    }

    public Integer getRepeatFlag() {
        return repeatFlag;
    }

    public void setRepeatFlag(Integer repeatFlag) {
        this.repeatFlag = repeatFlag;
    }

    public Integer getNullFlag() {
        return nullFlag;
    }

    public void setNullFlag(Integer nullFlag) {
        this.nullFlag = nullFlag;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getControlTypeId() {
        return controlTypeId;
    }

    public void setControlTypeId(Integer controlTypeId) {
        this.controlTypeId = controlTypeId;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }

    public Integer getChainFlag() {
        return chainFlag;
    }

    public void setChainFlag(Integer chainFlag) {
        this.chainFlag = chainFlag;
    }

    public Integer getChainDetailId() {
        return chainDetailId;
    }

    public void setChainDetailId(Integer chainDetailId) {
        this.chainDetailId = chainDetailId;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    public String getChainFieldName() {
        return chainFieldName;
    }

    public void setChainFieldName(String chainFieldName) {
        this.chainFieldName = chainFieldName;
    }

    public List<BaseDetail> getComboboxList() {
        return comboboxList;
    }

    public void setComboboxList(List<BaseDetail> comboboxList) {
        this.comboboxList = comboboxList;
    }

    public Integer getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(Integer saveFlag) {
        this.saveFlag = saveFlag;
    }
}
