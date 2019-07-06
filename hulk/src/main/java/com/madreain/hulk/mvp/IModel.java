package com.madreain.hulk.mvp;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */
public interface IModel {
    /**
     * 在框架中 {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
     */
    void onDestroy();
}
