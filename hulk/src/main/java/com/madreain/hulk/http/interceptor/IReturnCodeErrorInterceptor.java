package com.madreain.hulk.http.interceptor;


import com.madreain.hulk.mvp.IView;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：接口请求returnCode不正确的情况拦截处理
 */
public interface IReturnCodeErrorInterceptor {
    /**
     * 根据返回的returnCode，判断是否进行拦截
     * @param returnCode
     * @return
     */
    boolean intercept(String returnCode);

    /**
     * intercept(String returnCode)方法为true的时候调用该方法
     * @param iView
     * @param returnCode
     * @param msg
     */
    void todo(IView iView, String returnCode, String msg);

}
