package com.waoqi.msettopboxs.bean;

import com.waoqi.mvp.net.IModel;

public class BasePresponce<T> implements IModel {
    private int retcode;
    private String msg;
    private T data;
    private boolean success;


    @Override
    public boolean isNull() {
        return data == null;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return success ? null : msg;
    }


    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }
}
