package com.vanguard8.framework.vo;

public class DictCondition {
    private Integer flag; //区分标志
    private String condition; //查询条件
    private Integer userId;  //用户id，用来处理权限

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
