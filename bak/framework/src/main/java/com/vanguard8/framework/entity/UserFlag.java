package com.vanguard8.framework.entity;

import java.io.Serializable;

/**
 * @author 
 */
public class UserFlag implements Serializable {
    private Integer flagId;

    private String flagName;

    private static final long serialVersionUID = 1L;

    public Integer getFlagId() {
        return flagId;
    }

    public void setFlagId(Integer flagId) {
        this.flagId = flagId;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }
}