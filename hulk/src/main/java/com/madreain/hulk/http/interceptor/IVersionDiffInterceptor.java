package com.madreain.hulk.http.interceptor;


import com.madreain.hulk.mvp.IView;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：请求接口返回的versino版本和本地不一致 进行处理
 */
public interface IVersionDiffInterceptor {

    /**
     * 根据返回的version，判断是否进行拦截
     * @param version
     * @return
     */
    boolean intercept(String version);

    /**
     * intercept(String returnCode)方法为true的时候调用该方法
     * @param iView
     * @param serviceVersion
     */
    void todo(IView iView, String serviceVersion);

}
