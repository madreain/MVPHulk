package com.madreain.hulk.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.madreain.hulk.mvp.IPresenter;
import com.madreain.hulk.mvp.IView;
import com.madreain.hulk.utils.T;
import com.madreain.hulk.view.varyview.IVaryViewHelperController;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;


/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：基于rxlifecycle2中的RxAppCompatActivity
 */
public abstract class LibActivity<P extends IPresenter> extends RxAppCompatActivity implements IView {

    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract void init(Bundle savedInstanceState);

    @Inject
    public P presenter;

    private IVaryViewHelperController viewController;

    protected abstract IVaryViewHelperController initVaryViewHelperController();

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            AndroidInjection.inject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        viewController = initVaryViewHelperController();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (presenter != null)
            presenter.onDestroy();
        this.presenter = null;
    }

    @Override
    public void showLoading() {
        viewController.showLoading();
    }

    @Override
    public void showLoading(String msg) {
        viewController.showLoading(msg);
    }

    @Override
    public void showEmpty(String content) {
        viewController.showEmpty(content);
    }

    @Override
    public void showEmpty(String content, View.OnClickListener clickListener) {
        viewController.showEmpty(content, clickListener);
    }

    @Override
    public void showNetworkError(View.OnClickListener listener) {
        viewController.showNetworkError(listener);
    }

    @Override
    public void showNetworkError(String msg, View.OnClickListener listener) {
        viewController.showNetworkError(msg, listener);
    }

    @Override
    public void showCustomView(int drawableInt, String title, String msg, String btnText, View.OnClickListener listener) {
        viewController.showCustomView(drawableInt, title, msg, btnText, listener);
    }

    @Override
    public void restore() {
        viewController.restore();
    }

    @Override
    public boolean isHasRestore() {
        return viewController.isHasRestore();
    }

    @Override
    public void showToast(String msg) {
        T.showShortToastSafe(getHulkActivity(), msg);
    }

    @Override
    public void showToast(int msg) {
        T.show(getHulkActivity(), msg);
    }

    @Override
    public Activity getHulkActivity() {
        return this;
    }

    @Override
    public Context getHulkContext() {
        return this;
    }

    @Override
    public Context getHulkAppContext() {
        return getApplicationContext();
    }

}
