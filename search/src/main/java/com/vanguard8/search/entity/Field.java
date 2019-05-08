package com.vanguard8.search.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class Field implements Serializable {
    private Integer fieldId;

    private Integer modelId;

    private String fieldName;

    private String fieldCode;

    private Byte fieldTypeId;

    private Integer orderValue;

    private Integer showWidth;

    private Boolean showFooter;

    private Byte footerTypeId;

    private String footerStr;

    private String align;

    private static final long serialVersionUID = 1L;

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public Byte getFieldTypeId() {
        return fieldTypeId;
    }

    public void setFieldTypeId(Byte fieldTypeId) {
        this.fieldTypeId = fieldTypeId;
    }

    public Integer getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }

    public Integer getShowWidth() {
        return showWidth;
    }

    public void setShowWidth(Integer showWidth) {
        this.showWidth = showWidth;
    }

    public Boolean getShowFooter() {
        return showFooter;
    }

    public void setShowFooter(Boolean showFooter) {
        this.showFooter = showFooter;
    }

    public Byte getFooterTypeId() {
        return footerTypeId;
    }

    public void setFooterTypeId(Byte footerTypeId) {
        this.footerTypeId = footerTypeId;
    }

    public String getFooterStr() {
        return footerStr;
    }

    public void setFooterStr(String footerStr) {
        this.footerStr = footerStr;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }
}