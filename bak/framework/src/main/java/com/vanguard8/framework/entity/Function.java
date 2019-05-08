package com.vanguard8.framework.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class Function implements Serializable {
    /**
     * 编码
     */
    private String funcId;

    /**
     * 级别码
     */
    private String funcCode;

    /**
     * 模块名称
     */
    private String funcName;

    /**
     * url
     */
    private String funcPath;

    /**
     * 1是模块0是目录不是最终模块
     */
    private Byte isLast;

    //扩展字段：当前岗位是否具备该权限，0则不具备，其他则表示具备
    private Integer extField;

    private static final long serialVersionUID = 1L;

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncPath() {
        return funcPath;
    }

    public void setFuncPath(String funcPath) {
        this.funcPath = funcPath;
    }

    public Byte getIsLast() {
        return isLast;
    }

    public void setIsLast(Byte isLast) {
        this.isLast = isLast;
    }

    public Integer getExtField() {
        return extField;
    }

    public void setExtField(Integer extField) {
        this.extField = extField;
    }
}