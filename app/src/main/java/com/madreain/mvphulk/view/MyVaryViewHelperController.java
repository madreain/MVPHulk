package com.madreain.mvphulk.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.madreain.hulk.view.varyview.IVaryViewHelperController;
import com.madreain.hulk.view.varyview.VaryViewHelper;
import com.madreain.mvphulk.R;


/**
 * @author madreain
 * @date 2019-07-15.
 * module：
 * description：View替换：loading,Empty,NetError......
 */
public class MyVaryViewHelperController implements IVaryViewHelperController {

    private VaryViewHelper helper;

    //是否已经调用过restore方法
    private boolean hasRestore;

    public boolean isHasRestore() {
        return hasRestore;
    }

    public MyVaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    private MyVaryViewHelperController(VaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    @Override
    public void showNetworkError(View.OnClickListener onClickListener) {
        showNetworkError("网络状态异常，请刷新重试", onClickListener);
    }

    @Override
    public void showNetworkError(String msg, View.OnClickListener onClickListener) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_error);
        Button againBtn = layout.findViewById(R.id.pager_error_loadingAgain);
        TextView tv_title = layout.findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        TextView tv_msg = layout.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener);
        }
        helper.showView(layout);
    }

    @Override
    public void showCustomView(int drawableInt, String title, String msg, String btnText, View.OnClickListener listener) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_error);
        ImageView iv_flag = layout.findViewById(R.id.iv_flag);
        TextView tv_title = layout.findViewById(R.id.tv_title);
        TextView tv_msg = layout.findViewById(R.id.tv_msg);
        Button againBtn = layout.findViewById(R.id.pager_error_loadingAgain);

        iv_flag.setImageResource(drawableInt);
        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }

        if (TextUtils.isEmpty(msg)) {
            tv_msg.setVisibility(View.GONE);
        } else {
            tv_msg.setVisibility(View.VISIBLE);
            tv_msg.setText(msg);
        }

        if (TextUtils.isEmpty(btnText)) {
            againBtn.setVisibility(View.GONE);
        } else {
            againBtn.setText(btnText);
            if (null != listener) {
                againBtn.setOnClickListener(listener);
            }
        }
        helper.showView(layout);
    }

    @Override
    public void showEmpty(String emptyMsg) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_no_data);
        TextView textView = layout.findViewById(R.id.tv_no_data);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        }
        helper.showView(layout);
    }

    @Override
    public void showEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_no_data_click);
        Button againBtn = layout.findViewById(R.id.pager_error_loadingAgain);
        TextView textView = layout.findViewById(R.id.tv_no_data);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        }
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener);
//            againBtn.setVisibility(View.VISIBLE);
            againBtn.setVisibility(View.GONE);//按钮都隐藏，空页面没有刷新 2018.9.5
        } else {
            againBtn.setVisibility(View.GONE);
        }
        helper.showView(layout);
    }

    @Override
    public void showLoading() {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_loading);
        helper.showView(layout);
    }

    @Override
    public void showLoading(String msg) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_loading);
        TextView tv_msg = layout.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        helper.showView(layout);
    }

    public void restore() {
        hasRestore = true;
        helper.restoreView();
    }

}
