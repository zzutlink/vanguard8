package com.vanguard8.framework.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
public class User implements Serializable {
    /**
     * 编码
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 盐
     */
    private String salt;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 对应岗位
     */
    //private Integer dsId;
    private Deptsta deptsta;

    /**
     * 状态，对应sys_user_flag
     */
    //private Integer flagId;
    private UserFlag userFlag;

    /**
     * 工号
     */
    private String workNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 连续登录错误次数
     */
    private Integer errorCount;

    private Dept dept;

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Deptsta getDeptsta() {
        return deptsta;
    }

    public void setDeptsta(Deptsta deptsta) {
        this.deptsta = deptsta;
    }

    public UserFlag getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(UserFlag userFlag) {
        this.userFlag = userFlag;
    }


    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" [");
        sb.append("userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", loginName=").append(loginName);
        sb.append(", salt=").append(salt);
        sb.append(", password=").append(password);
        sb.append(", workNo=").append(workNo);
        sb.append("]");
        return sb.toString();
    }
}