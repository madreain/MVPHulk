package com.madreain.hulk.mvp;

import android.support.annotation.Keep;


/**
 * @author madreain
 * @date 2019-07-6.
 * module：接口返回封装类
 * description：
 */
@Keep
public class BaseRes<T> implements IRes<T> {

    private T data;
    private String code;
    private String msg;
    private String version;

    @Override
    public T getResult() {
        return data;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setResult(T data) {
        this.data = data;
    }

    @Override
    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}