package com.vanguard8.framework.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class StaFunction implements Serializable {
    private Integer dsId;

    private String funcId;

    private static final long serialVersionUID = 1L;

    public Integer getDsId() {
        return dsId;
    }

    public void setDsId(Integer dsId) {
        this.dsId = dsId;
    }

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" [");
        sb.append("dsId=").append(dsId);
        sb.append(", funcId=").append(funcId);
        sb.append("]");
        return sb.toString();
    }
}