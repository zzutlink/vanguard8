package com.vanguard8.common;

public class ResultGenerator {
    private static final int RESULT_CODE_SUCCESS = 1;  // 成功处理请求
    private static final int RESULT_CODE_FAIL = 0;

    private static final String DEFAULT_SUCCESS_MESSAGE = "操作成功！";
    private static final String DEFAULT_FAIL_MESSAGE = "操作失败！";

    public static Result genSuccessResult() {
        Result result = new Result();
        result.setSuccess(true);
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    public static Result genSuccessResult(Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setResultCode(RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static Result genFailResult(String message) {
        Result result = new Result();
        result.setSuccess(false);
        result.setResultCode(RESULT_CODE_FAIL);
        if (message == null || message.length() < 1) {
            message = DEFAULT_FAIL_MESSAGE;
        } else {
            message = "操作失败：".concat(message);
        }
        result.setMessage(message);
        return result;
    }
}
