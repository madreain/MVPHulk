package com.madreain.hulk.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.madreain.hulk.R;
import com.madreain.hulk.base.LibActivity;
import com.madreain.hulk.mvp.BasePresenter;
import com.madreain.hulk.utils.ActivityUtils;
import com.madreain.hulk.utils.EventBusUtils;
import com.madreain.hulk.utils.ARouterUtils;
import com.madreain.hulk.utils.StringUtils;
import com.madreain.hulk.view.varyview.IVaryViewHelperController;
import com.madreain.hulk.view.varyview.VaryViewHelperController;


/**
 * @author madreain
 * @date 2019-07-05.
 * module：
 * description：
 */

public abstract class BaseActivity<P extends BasePresenter> extends LibActivity<P> {

    private ProgressDialog dialog;

    /**
     * @return 该View 替换为显示loadingView 或者 emptyView 或者 netWorkErrorView
     */
    public abstract View getReplaceView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ActivityUtils.get().addActivity(this);
        ARouterUtils.inject(this);
        EventBusUtils.register(this);
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
        ActivityUtils.get().remove(this);
    }

    @Override
    public void showTips(String msg) {
        TSnackbar snackBar = TSnackbar.make(findViewById(android.R.id.content), msg, TSnackbar.LENGTH_SHORT);
        View snackBarView = snackBar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        TextView textView = snackBarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.m90EE90));
        snackBar.show();
    }

    @Override
    protected IVaryViewHelperController initVaryViewHelperController() {
        return new VaryViewHelperController(getReplaceView());
    }

    @Override
    public void showDialogProgress(String msg) {
        showDialogProgress(msg, true, null);
    }

    @Override
    public void showDialogProgress(String msg, boolean cancelable, DialogInterface.OnCancelListener onCancelListener) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(cancelable);
                dialog.setCanceledOnTouchOutside(cancelable);
                dialog.setOnCancelListener(onCancelListener);
            }
            if (!TextUtils.isEmpty(msg))
                dialog.setMessage(msg);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }


    //以下是ActionBar的封装

    /**
     * 初始化设置Title为""
     *
     * @param toolbar
     */
    public void setSupportActionBar(Toolbar toolbar) {
        toolbar.setTitle("");
        getDelegate().setSupportActionBar(toolbar);
    }

    /**
     * ToolBar 返回按钮
     *
     * @param toolbar
     */
    public void setSupportActionBarWithBack(Toolbar toolbar) {
        setSupportActionBarWithBack(toolbar, R.mipmap.hulk_back);
    }

    /**
     * ToolBar 返回按钮
     *
     * @param toolbar
     * @param listener
     */
    public void setSupportActionBarWithBack(Toolbar toolbar, View.OnClickListener listener) {
        setSupportActionBarWithBack(toolbar, R.mipmap.hulk_back, listener);
    }

    /**
     * ToolBar 返回按钮
     *
     * @param toolbar
     * @param backIconId
     */
    public void setSupportActionBarWithBack(Toolbar toolbar, int backIconId) {
        setSupportActionBarWithBack(toolbar, backIconId, v -> onBackPressed());
    }

    /**
     * ToolBar 返回按钮
     *
     * @param toolbar
     * @param backIconId
     * @param listener
     */
    public void setSupportActionBarWithBack(Toolbar toolbar, int backIconId, final View.OnClickListener listener) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(backIconId);
        toolbar.setNavigationOnClickListener(listener);
        toolbar.setNavigationOnClickListener(listener);
    }

    /**
     * 居中Title设置
     *
     * @param toolbar
     * @param titleText
     */
    public TextView setCenterTitleText(Toolbar toolbar, String titleText) {
        TextView textView_title = toolbar.findViewById(R.id.tv_title);
        textView_title.setText(titleText);
        return textView_title;
    }

    /**
     * 设置是否显示阴影
     *
     * @param toolbar
     * @param show
     */
    public void setToolbarShadow(Toolbar toolbar, boolean show) {
        View toolbarShadow = ((RelativeLayout) toolbar.getParent()).findViewById(R.id.toolbarShadow);
        toolbarShadow.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 默认位置Title设置
     *
     * @param toolbar
     * @param titleText
     */
    public void setToolBarTitleText(Toolbar toolbar, String titleText) {
        toolbar.setTitle(titleText);
    }

    /**
     * toolbar设置左侧为文字
     *
     * @param toolbar
     * @param leftText
     * @param listener
     */
    public TextView setToolBarLeftText(Toolbar toolbar, String leftText, View.OnClickListener listener) {
        TextView tvLeft = toolbar.findViewById(R.id.tv_left);
        if (!StringUtils.isEmpty(leftText)) {
            tvLeft.setText(leftText);
            tvLeft.setVisibility(View.VISIBLE);
        } else {
            tvLeft.setVisibility(View.GONE);
        }
        tvLeft.setOnClickListener(listener);
        return tvLeft;
    }

    /**
     * toolbar设置右侧为文字
     *
     * @param toolbar
     * @param rightText
     * @param listener
     */
    public TextView setToolBarRightText(Toolbar toolbar, String rightText, View.OnClickListener listener) {
        TextView tvRight = toolbar.findViewById(R.id.tv_right);
        if (!StringUtils.isEmpty(rightText)) {
            tvRight.setText(rightText);
            tvRight.setVisibility(View.VISIBLE);
        } else {
            tvRight.setVisibility(View.GONE);
        }
        tvRight.setOnClickListener(listener);
        return tvRight;
    }

}
