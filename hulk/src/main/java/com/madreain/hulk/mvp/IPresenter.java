package com.madreain.hulk.mvp;

import android.app.Activity;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */
public interface IPresenter {
    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    void onDestroy();
}
