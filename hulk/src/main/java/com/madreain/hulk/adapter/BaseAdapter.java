package com.madreain.hulk.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.madreain.hulk.mvp.IView;
import com.madreain.hulk.view.baseviewholder.HulkViewHolder;

import java.util.List;

import javax.inject.Inject;

/**
 * @author madreain
 * @date 2019-07-05.
 * module：
 * description：
 */
public abstract class BaseAdapter<T, V extends IView> extends BaseQuickAdapter<T, HulkViewHolder> {

    public BaseAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    @Inject
    public V view;

    public Context getContext() {
        return mContext;
    }

    public void clearData() {
        getData().clear();
        notifyDataSetChanged();
    }

}