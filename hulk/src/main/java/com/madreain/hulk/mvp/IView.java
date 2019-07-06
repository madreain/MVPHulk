package com.madreain.hulk.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.madreain.hulk.view.varyview.IVaryViewHelperController;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */
public interface IView extends IVaryViewHelperController {

    /**
     * 显示提示文字
     * @param msg
     */
    void showTips(String msg);

    /**
     * 显示对话框
     * @param msg
     */
    void showDialogProgress(String msg);

    /**
     * 显示对话框,dismiss监听
     *
     * @param msg        消息内容
     * @param cancelable dialog能否消失
     */
    void showDialogProgress(String msg, boolean cancelable, DialogInterface.OnCancelListener onCancelListener);

    /**
     * 对话框消失
     */
    void dismissDialog();

    /**
     * 吐司显示
     * @param msg
     */
    void showToast(String msg);

    /**
     * 吐司显示
     * @param msg
     */
    void showToast(int msg);

    /**
     * 是否已经调用过restore()方法
     * @return
     */
    boolean isHasRestore();

    /**
     * 获取activity
     * @return
     */
    Activity getHulkActivity();

    /**
     * 获取activity的context
     * @return
     */
    Context getHulkContext();

    /**
     * 获取app的context
     * @return
     */
    Context getHulkAppContext();

}
