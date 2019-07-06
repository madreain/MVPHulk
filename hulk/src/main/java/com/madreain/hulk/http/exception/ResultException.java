package com.madreain.hulk.http.exception;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：自定义数据返回异常类,制定api接口调用时返回的BaseRes中的泛型T为null或者ListSize为0这两种情况为异常情况
 */
public class ResultException extends Exception {

    /**
     * 数据返回异常
     */
    public ResultException() {
        super();
    }

    /**
     * 数据返回异常传入错误内容
     * @param msg
     */
    public ResultException(String msg) {
        super(msg);
    }

}
