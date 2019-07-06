package com.madreain.hulk.mvp;

import android.content.Context;

import javax.inject.Inject;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */
public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter {

    @Inject
    protected M model;

    @Inject
    protected V view;

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    protected Context getContext() {
        return view.getHulkContext();
    }

    protected Context getActivity() {
        return view.getHulkActivity();
    }

    protected Context getAppContext() {
        return view.getHulkAppContext();
    }

}