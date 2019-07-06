package com.madreain.hulk.mvp;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：接口返回数据的结构体
 * description：
 */
public interface IRes<T> {

    String getMsg();

    String getCode();

    T getResult();

    String getVersion();
}
