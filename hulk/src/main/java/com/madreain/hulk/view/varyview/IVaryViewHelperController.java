package com.madreain.hulk.view.varyview;

import android.view.View;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：变化view辅助控制接口
 */
public interface IVaryViewHelperController {

    /**
     * 显示界面loading View
     */
    void showLoading();

    /**
     * 显示界面loading
     *
     * @param msg loading文字描述
     */
    void showLoading(String msg);

    /**
     * 显示empty View
     *
     * @param emptyMsg empty View 文字描述
     */
    void showEmpty(String emptyMsg);

    /**
     * 显示empty View
     *
     * @param emptyMsg empty View 文字描述
     * @param listener empty View 点击重试按钮
     */
    void showEmpty(String emptyMsg, View.OnClickListener listener);

    /**
     * 显示netWorkError View
     *
     * @param listener netWorkError View 点击重试按钮
     */
    void showNetworkError(View.OnClickListener listener);

    /**
     * 显示netWorkError View
     *
     * @param msg      netWorkError View 文字描述
     * @param listener netWorkError View 点击重试按钮
     */
    void showNetworkError(String msg, View.OnClickListener listener);

    /**
     * 显示简单的自定义view
     * @param drawableInt 图标
     * @param title       图标下面一行的文字
     * @param msg         图标下面2行的文字信息
     * @param btnText     按钮文字信息
     * @param listener    按钮点击事件
     */
    void showCustomView(int drawableInt, String title, String msg, String btnText, View.OnClickListener listener);

    /**
     * 恢复显示原本View  即每个Activity中实现的getReplaceView
     */
    void restore();


    /**
     * 是否已经调用过restore()方法
     */
    boolean isHasRestore();

}
