package com.madreain.hulk.view.varyview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：变化view辅助类用于增删view
 */
public class VaryViewHelper implements IVaryViewHelper{

    //要替换的view
    private View view;
    //替换view的夫view容器
    private ViewGroup parentView;
    //要替换的view的下角标
    private int viewIndex;
    //夫容器的布局参数
    private ViewGroup.LayoutParams params;
    //当前显示的view
    private View currentView;

    public VaryViewHelper(View view) {
        super();
        this.view = view;
    }

    /**
     * 当前显示的view
     * @return
     */
    @Override
    public View getCurrentView() {
        return currentView;
    }

    /**
     * 需替换的布局view
     * @return
     */
    @Override
    public View getView() {
        return view;
    }

    /**
     * 恢复到需替换的布局view
     */
    @Override
    public void restoreView() {
        showView(view);
    }

    /**
     * 设置要展示的view
     * @param view
     */
    @Override
    public void showView(View view) {
        if (parentView == null) {
            init();
        }
        this.currentView = view;
        if (parentView.getChildAt(viewIndex) != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            parentView.removeViewAt(viewIndex);
            parentView.addView(view, viewIndex, params);
        }
    }

    /**
     * 设置要加载的布局layoutId
     * @param layoutId
     * @return
     */
    @Override
    public View inflate(int layoutId) {
        return LayoutInflater.from(view.getContext()).inflate(layoutId, null);
    }

    /**
     * 上下文
     * @return
     */
    @Override
    public Context getContext() {
        return view.getContext();
    }

    private void init() {
        params = view.getLayoutParams();
        if (view.getParent() != null) {
            parentView = (ViewGroup) view.getParent();
        } else {
            parentView = view.getRootView().findViewById(android.R.id.content);
        }
        int count = parentView.getChildCount();
        for (int index = 0; index < count; index++) {
            if (view == parentView.getChildAt(index)) {
                viewIndex = index;
                break;
            }
        }
        currentView = view;
    }

}
