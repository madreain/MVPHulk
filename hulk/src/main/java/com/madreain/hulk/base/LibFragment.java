package com.madreain.hulk.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.madreain.hulk.mvp.IPresenter;
import com.madreain.hulk.mvp.IView;
import com.madreain.hulk.utils.T;
import com.madreain.hulk.view.varyview.IVaryViewHelperController;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * @param <P> 自定义Presenter
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：基于对rxlifecycle2中的RxFragment封装
 */
public abstract class LibFragment<P extends IPresenter> extends RxFragment implements IView {

    public abstract
    @LayoutRes
    int getLayoutId();

    public View rootView;

    @Inject
    public P presenter;

    private IVaryViewHelperController viewController;

    protected abstract IVaryViewHelperController initVaryViewHelperController();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            AndroidSupportInjection.inject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ButterKnife.bind(this, view);
        viewController = initVaryViewHelperController();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.onDestroy();
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
        T.show(getHulkActivity(), msg);
    }

    @Override
    public void showToast(int msg) {
        T.show(getHulkActivity(), msg);
    }

    @Override
    public Activity getHulkActivity() {
        return getActivity();
    }

    @Override
    public Context getHulkContext() {
        return getContext();
    }

    @Override
    public Context getHulkAppContext() {
        return getActivity().getApplicationContext();
    }

}
