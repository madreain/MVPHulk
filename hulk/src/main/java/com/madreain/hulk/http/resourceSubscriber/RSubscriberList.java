package com.madreain.hulk.http.resourceSubscriber;



import android.support.annotation.IntDef;


import com.madreain.hulk.config.HulkConfig;
import com.madreain.hulk.http.exception.NetWorkException;
import com.madreain.hulk.http.exception.ResultException;
import com.madreain.hulk.http.exception.ReturnCodeException;
import com.madreain.hulk.http.interceptor.IReturnCodeErrorInterceptor;
import com.madreain.hulk.mvp.IListView;
import com.madreain.hulk.mvp.IView;
import com.madreain.hulk.utils.NetworkUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：接口数据返回对象集合
 * description：
 */
public abstract class RSubscriberList<T> extends RSubscriberAbstract<T> {
    private IView iView;
    private int pageNo = 0;

    private String emptyMsg;
    private String errorMsg;

    @IntDef({NULL, TOAST, REPLACE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Type
    int type = REPLACE;//接口操作交互类型
    public static final int NULL = 0;//无交互
    public static final int TOAST = 1;//接口开始showDialogProgress()---->>接口结束 dismissDialog() 错误Toast
    public static final int REPLACE = 2;//接口开始showLoading()---->>接口结束 :成功：restore(),失败：showError();

    private RSubscriberList() {
    }

    public RSubscriberList(IView iView, int pageNo) {
        this(iView, pageNo, REPLACE);
    }

    public RSubscriberList(IView iView, int pageNo, String emptyMsg, String errorMsg) {
        this(iView, pageNo, REPLACE, emptyMsg, errorMsg);
    }

    public RSubscriberList(IView iView, int pageNo, @Type int type) {
        this(iView, pageNo, type, "暂无数据", "网络错误");
    }

    public RSubscriberList(IView iView, int pageNo, @Type int type, String emptyMsg, String errorMsg) {
        this.iView = iView;
        this.pageNo = pageNo;
        this.type = type;
        this.emptyMsg = emptyMsg;
        this.errorMsg = errorMsg;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (pageNo == 1) {
            switch (type) {
                case NULL:
                    break;
                case TOAST:
                    iView.showDialogProgress("");
                    break;
                case REPLACE:
                    if (!iView.isHasRestore()) {
                        iView.showLoading();
                    }
                    break;
            }
        }
    }

    @Override
    public final void onNext(T t) {
        if (pageNo == 1) {
            switch (type) {
                case NULL:
                    break;
                case TOAST:
                    iView.dismissDialog();
                    break;
                case REPLACE:
                    if (!iView.isHasRestore()) {
                        iView.restore();
                    }
                    break;
            }
        }
        _onNext(t);
    }

    @Override
    public void onError(Throwable t) {
        /*如果网络未连接不会调用flatMap 所以网络未连接不会出现ServerException错误*/
        if (!NetworkUtils.isConnected()) {
            _onNetWorkError();//没网
        } else {
            if (t instanceof NetWorkException) {
                _onNetWorkError();//没网
            } else if (t instanceof ReturnCodeException) {
                isIntercepted(t);
                _onReturnCodeError(((ReturnCodeException) t).getReturnCode(), t.getMessage());
            } else if (t instanceof ResultException) {
                _onTEmpty();
            } else {
                _onNetWorkError();//UnknownHostException 1：服务器地址错误；2：网络未连接
            }
        }
    }

    @Override
    public void _onTEmpty() {
        switch (type) {
            case NULL:
                break;
            case TOAST:
                if (!iView.isHasRestore()) {
                    iView.showToast(emptyMsg);
                    iView.dismissDialog();
                }
                break;
            case REPLACE:
                iView.showEmpty(emptyMsg, view -> retry());
                break;
        }
    }

    @Override
    public void _onNetWorkError() {
        switch (type) {
            case NULL:
                break;
            case TOAST:
                iView.showToast(errorMsg);
                iView.dismissDialog();
                break;
            case REPLACE:
                if (pageNo == 1 && iView.isHasRestore()) {
                    if (iView instanceof IListView) {
                        ((IListView) iView).refreshComplete();
                    }
                    iView.showTips(errorMsg);
                } else {
                    iView.showNetworkError(errorMsg, view -> retry());
                }
                break;
        }
    }

    @Override
    public void _onReturnCodeError(String returnCode, String msg) {
        switch (type) {
            case NULL:
                break;
            case TOAST:
                iView.showToast(msg);
                iView.dismissDialog();
                break;
            case REPLACE:
                if (pageNo == 1 && iView.isHasRestore()) {
                    if (iView instanceof IListView) {
                        ((IListView) iView).refreshComplete();
                    }
                    iView.showTips(errorMsg);
                } else {
                    iView.showNetworkError(msg, view -> retry());
                }
                break;
        }
    }

    @Override
    public void onComplete() {
        if (!iView.isHasRestore() && pageNo == 1) {
            switch (type) {
                case NULL:
                    break;
                case TOAST:
                    iView.dismissDialog();
                    break;
                case REPLACE:
                    break;
            }
        }
        dispose();
    }

    /**
     * 判断是否被拦截
     *
     * @param t Throwable
     */
    private boolean isIntercepted(Throwable t) {
        boolean isIntercepted = false;//是否被拦截了
        for (IReturnCodeErrorInterceptor interceptor : HulkConfig.getInterceptors()) {
            if (interceptor.intercept(((ReturnCodeException) t).getReturnCode())) {
                isIntercepted = true;
                interceptor.todo(iView, ((ReturnCodeException) t).getReturnCode(), t.getMessage());
                break;
            }
        }
        return isIntercepted;
    }
}
