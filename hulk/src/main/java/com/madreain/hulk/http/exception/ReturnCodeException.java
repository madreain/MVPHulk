package com.madreain.hulk.http.exception;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：自定义响应码异常类：api接口调用时服务器操作异常，返回异常响应码
 */
public class ReturnCodeException extends Exception {

    private String returnCode;

    /**
     * 响应码异常，返回异常的响应码及其显示错误的提示
     * @param returnCode
     * @param msg
     */
    public ReturnCodeException(String returnCode, String msg) {
        super(msg);
        this.returnCode = returnCode;
    }

    public String getReturnCode() {
        return returnCode;
    }

}
