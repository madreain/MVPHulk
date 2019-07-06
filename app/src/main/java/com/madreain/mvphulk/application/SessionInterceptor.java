package com.madreain.mvphulk.application;

import com.madreain.hulk.http.interceptor.IReturnCodeErrorInterceptor;
import com.madreain.hulk.mvp.IView;


/**
 * @author madreain
 * @date 2019/3/4.
 * module：
 * description：returnCode返回session_100 拦截处理
 */
public class SessionInterceptor implements IReturnCodeErrorInterceptor {

    //和接口定义互踢的相关参数返回，然后在todo方法进行跳转

    @Override
    public boolean intercept(String code) {
        //这里的-100表示互踢的返回参数
        return "-100".equals(code);
    }

    @Override
    public void todo(IView iView, String returnCode, String msg) {

    }

}
