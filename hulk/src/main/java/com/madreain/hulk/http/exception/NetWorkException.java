package com.madreain.hulk.http.exception;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description： 自定义网络错误异常类
 */
public class NetWorkException extends Exception {

    /**
     * 网络错误
     */
    public NetWorkException() {
        super();
    }

    /**
     * 网络错误传入错误内容
     * @param msg
     */
    public NetWorkException(String msg) {
        super(msg);
    }

}
