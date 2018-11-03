package com.vanguard8.framework.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class Deptsta implements Serializable {
    /**
     * 编码
     */
    private Integer dsId;

    /**
     * 级别码例如001，001001
     */
    private String dsCode;

    /**
     * 名称
     */
    private String dsName;

    /**
     * 完整名称（从最高级延续下来的）
     */
    private String dsTotalName;

    /**
     * 0代表部门1代表岗位
     */
    private Byte dsFlag;

    /**
     * 是否锁定，0未锁定1系统设定不允许变更2复用岗位
     */
    private Byte lockFlag;

    //下级节点数量
    private int childCount;

    private static final long serialVersionUID = 1L;

    public int getChildCount() {
        return childCount;
    }

    public Integer getDsId() {
        return dsId;
    }

    public void setDsId(Integer dsId) {
        this.dsId = dsId;
    }

    public String getDsCode() {
        return dsCode;
    }

    public void setDsCode(String dsCode) {
        this.dsCode = dsCode;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getDsTotalName() {
        return dsTotalName;
    }

    public void setDsTotalName(String dsTotalName) {
        this.dsTotalName = dsTotalName;
    }

    public Byte getDsFlag() {
        return dsFlag;
    }

    public void setDsFlag(Byte dsFlag) {
        this.dsFlag = dsFlag;
    }

    public Byte getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(Byte lockFlag) {
        this.lockFlag = lockFlag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" [");
        sb.append("dsId=").append(dsId);
        sb.append(", dsCode=").append(dsCode);
        sb.append(", dsName=").append(dsName);
        sb.append(", dsTotalName=").append(dsTotalName);
        sb.append(", dsFlag=").append(dsFlag);
        sb.append(", lockFlag=").append(lockFlag);
        sb.append("]");
        return sb.toString();
    }
}