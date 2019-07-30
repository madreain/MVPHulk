package com.madreain.hulk.view.varyview;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.madreain.hulk.R;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：变化view的默认辅助控制类（如果不使用默认的可实现IVaryViewHelperController自定义）
 * description：View替换：loading显示隐藏、无数据的展示view、网络异常的view、简单自定义的view
 */
public class VaryViewHelperController implements IVaryViewHelperController{

    private VaryViewHelper helper;

    //是否已经调用过restore方法
    private boolean hasRestore;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    private VaryViewHelperController(VaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    @Override
    public void showLoading() {
        hasRestore = false;
        View layout = helper.inflate(R.layout.hulk_page_loading);
        helper.showView(layout);
    }

    @Override
    public void showLoading(String msg) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.hulk_page_loading);
        TextView tv_msg = layout.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        helper.showView(layout);
    }

    @Override
    public void showEmpty(String emptyMsg) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.hulk_page_no_data);
        TextView textView = layout.findViewById(R.id.tv_no_data);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        }
        helper.showView(layout);
    }

    @Override
    public void showEmpty(String emptyMsg, View.OnClickListener listener) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.hulk_page_no_data_click);
        Button againBtn = layout.findViewById(R.id.pager_error_loadingAgain);
        TextView textView = layout.findViewById(R.id.tv_no_data);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        }
        if (null != listener) {
            againBtn.setOnClickListener(listener);
//            againBtn.setVisibility(View.VISIBLE);
            againBtn.setVisibility(View.GONE);//按钮都隐藏，空页面没有刷新
        } else {
            againBtn.setVisibility(View.GONE);
        }
        helper.showView(layout);
    }

    @Override
    public void showNetworkError(View.OnClickListener listener) {
        showNetworkError("网络状态异常，请刷新重试", listener);
    }

    @Override
    public void showNetworkError(String msg, View.OnClickListener listener) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.hulk_page_error);
        Button againBtn = layout.findViewById(R.id.pager_error_loadingAgain);
        TextView tv_title = layout.findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        TextView tv_msg = layout.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        if (null != listener) {
            againBtn.setOnClickListener(listener);
        }
        helper.showView(layout);
    }

    @Override
    public void showCustomView(int drawableInt, String title, String msg, String btnText, View.OnClickListener listener) {

    }

    @Override
    public void restore() {
        hasRestore = true;
        helper.restoreView();
    }

    @Override
    public boolean isHasRestore() {
        return hasRestore;
    }
}
