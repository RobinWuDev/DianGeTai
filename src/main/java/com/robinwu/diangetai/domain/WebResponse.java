package com.robinwu.diangetai.domain;

/**
 * Created by Robin on 16/6/15.
 */
public class WebResponse {
    public static final int OK_CODE = 0;
    public static final int ERROR_CODE = -1;

    private int code;
    private Object data;
    private String message;

    public WebResponse(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static WebResponse getOKResponse(Object data) {
        WebResponse response = new WebResponse(OK_CODE,data,"It's Fine!");
        return response;
    }

    public static WebResponse getErrorResponse(String message) {
        WebResponse response = new WebResponse(ERROR_CODE,null,message);
        return response;
    }

}
