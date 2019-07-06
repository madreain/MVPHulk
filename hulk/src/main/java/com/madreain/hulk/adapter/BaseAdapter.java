package com.madreain.hulk.adapter;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.madreain.hulk.mvp.IView;
import com.madreain.hulk.utils.OSSPathUtils;
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