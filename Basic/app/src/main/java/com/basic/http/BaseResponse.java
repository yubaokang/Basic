package com.basic.http;

/**
 * Created by ybk on 2015/9/5.
 */
public class BaseResponse {
    private String msg;
    private int returnCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }
}
