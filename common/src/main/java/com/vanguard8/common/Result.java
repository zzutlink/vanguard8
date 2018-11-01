package com.vanguard8.common;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private final int SUCCESS_CODE = 1;
    private final int FAIL_CODE = 0;
    private final String SUCCESS_MESSAGE = "成功";
    private final String FAIL_MESSAGE = "失败";

    private static final long serialVersionUID = 1L;
    private boolean success;
    private int resultCode;
    private String message;
    private T data;

    public Result() {
    }

    public Result(boolean success, int resultCode, String message) {
        this.success = success;
        this.resultCode = resultCode;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
