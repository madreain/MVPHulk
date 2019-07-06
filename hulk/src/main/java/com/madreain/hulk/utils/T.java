package com.madreain.hulk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.Toast;


/**
 * @author madreain
 * @date 2019-07-04.
 * module： 吐司相关工具类
 * description：
 */
public class T {

    private T() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Toast sToast;
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static boolean isJumpWhenMore = true;

    /**
     * 吐司初始化
     *
     * @param isJumpWhenMore 当连续弹出吐司时，是要弹出新吐司还是只修改文本内容
     *                       <p>{@code true}: 弹出新吐司<br>{@code false}: 只修改文本内容</p>
     *                       <p>如果为{@code false}的话可用来做显示任意时长的吐司</p>
     */
    public static void init(boolean isJumpWhenMore) {
        T.isJumpWhenMore = isJumpWhenMore;
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortToastSafe(Context context, final CharSequence text) {
        sHandler.post(() -> show(context, text, Toast.LENGTH_SHORT));
    }

    /**
     * @param text 文本
     */
    public static void show(Context context, CharSequence text) {
//        show(text, Toast.LENGTH_LONG);
        showShortToastSafe(context, text);
    }

    /**
     * @param resId 资源Id
     */
    public static void show(Context context, @StringRes int resId) {
//        show(resId, Toast.LENGTH_SHORT);
        showShortToastSafe(context, Utils.getContext().getResources().getText(resId).toString());
    }

    /**
     * @param resId 资源Id
     * @param args  参数
     */
    public static void show(Context context, @StringRes int resId, Object... args) {
        show(context, resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * @param format 格式
     * @param args   参数
     */
    public static void show(Context context, String format, Object... args) {
        show(context, format, Toast.LENGTH_SHORT, args);
    }

    /**
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void show(Context context, @StringRes int resId, int duration) {
        show(context, context.getResources().getText(resId).toString(), duration);
    }

    /**
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(Context context, @StringRes int resId, int duration, Object... args) {
        show(context, String.format(context.getResources().getString(resId), args), duration);
    }

    /**
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(Context context, String format, int duration, Object... args) {
        show(context, String.format(format, args), duration);
    }

    /**
     * @param text     文本
     * @param duration 显示时长
     */
    @SuppressLint("ShowToast")
    private static void show(Context context, CharSequence text, int duration) {
        if (context == null) return;
        if (isJumpWhenMore) cancelToast();
        if (sToast == null) {
            sToast = Toast.makeText(context, text, duration);
            sToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            sToast.setText(text);
            sToast.setDuration(duration);
        }
        sToast.show();
    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}