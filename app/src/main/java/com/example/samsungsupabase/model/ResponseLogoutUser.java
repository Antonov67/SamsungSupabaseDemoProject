package com.example.samsungsupabase.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogoutUser {
    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("error_code")
    @Expose
    public String errorCode;
    @SerializedName("msg")
    @Expose
    public String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
