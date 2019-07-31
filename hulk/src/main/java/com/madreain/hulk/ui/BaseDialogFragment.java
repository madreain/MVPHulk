package com.madreain.hulk.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.androidadvance.topsnackbar.TSnackbar;
import com.madreain.hulk.R;
import com.madreain.hulk.base.LibDialogFragment;
import com.madreain.hulk.mvp.BasePresenter;
import com.madreain.hulk.utils.EventBusUtils;
import com.madreain.hulk.utils.ARouterUtils;
import com.madreain.hulk.view.varyview.IVaryViewHelperController;
import com.madreain.hulk.view.varyview.VaryViewHelperController;


/**
 * @author madreain
 * @date 2019-07-05.
 * module：
 * description：
 */

public abstract class BaseDialogFragment<P extends BasePresenter> extends LibDialogFragment<P> {

    private ProgressDialog dialog;

    /**
     * @return 该View 替换为显示loadingView 或者 emptyView 或者 netWorkErrorView
     */
    public abstract View getReplaceView();

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ARouterUtils.inject(this);
        EventBusUtils.register(this);
        super.onViewCreated(view, savedInstanceState);
        init(view, savedInstanceState);
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
                dialog = new ProgressDialog(getActivity());
                setCancelable(cancelable);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unRegister(this);
    }


    @Override
    public void showTips(String msg) {
        TSnackbar snackBar = TSnackbar.make(getActivity().findViewById(android.R.id.content), msg, TSnackbar.LENGTH_SHORT);
        View snackBarView = snackBar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        TextView textView = snackBarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.m90EE90));
        snackBar.show();
    }

}
