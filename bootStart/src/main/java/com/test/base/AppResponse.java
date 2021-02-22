package com.test.base;

public class AppResponse<T> {

    private Integer Code;
    private Boolean RequestResult;
    private T Data;

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public Boolean getRequestResult() {
        return RequestResult;
    }

    public void setRequestResult(Boolean requestResult) {
        RequestResult = requestResult;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public AppResponse(Integer code, Boolean requestResult, T data) {
        Code = code;
        RequestResult = requestResult;
        Data =  data;
    }

}
