package com.madreain.hulk.adapter;

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
public abstract class BaseAdapter<T, V extends IView> extends BaseLibAdapter<T, HulkViewHolder> {
    public BaseAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    @Inject
    public V view;

    @Override
    public void addClickListener() {
    }

}