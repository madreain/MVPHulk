package com.madreain.hulk.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.madreain.hulk.adapter.BaseAdapter;
import com.madreain.hulk.adapter.BaseMultiAdapter;
import com.madreain.hulk.mvp.BasePresenter;
import com.madreain.hulk.mvp.IListView;
import com.madreain.hulk.utils.ListUtil;
import com.madreain.hulk.view.baseviewholder.HulkViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import javax.inject.Inject;


/**
 * @author madreain
 * @date 2019-07-05.
 * module：
 * description：
 */

public abstract class BaseListFragment<P extends BasePresenter, A extends BaseQuickAdapter<D, HulkViewHolder>, D> extends BaseFragment<P> implements IListView<D> {
    protected int pageNum = 1;
    private boolean loadMoreEnable = true;
    protected int loadPageNum = 1;//当前正在加载的page，但是当前page接口还未做出响应

    private boolean refreshEnable = true;//是否能进行下拉刷新
    @Inject
    public A adapter;

    public abstract void _init(View view, Bundle savedInstanceState);

    public abstract void loadPageListData(int pageNo);

    public abstract SmartRefreshLayout getSmartRefreshLayout();

    public abstract RecyclerView getRecyclerView();

    public abstract RecyclerView.LayoutManager getLayoutManager();

    /**
     * 设置不分页
     *
     * @param loadMoreEnable 不能加载更多操作
     */
    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.loadMoreEnable = loadMoreEnable;
        getSmartRefreshLayout().setEnableLoadMore(loadMoreEnable);
    }

    public void setRefreshEnable(boolean refreshEnable) {
        this.refreshEnable = refreshEnable;
        getSmartRefreshLayout().setEnabled(this.refreshEnable);
    }

    /**
     * BaseListActivity默认将子类实现的{@link #getSmartRefreshLayout}方法返回的View设置为ReplaceView
     * 如果需要替换，子类实现该方法{@link #getReplaceView}
     *
     * @return 替换显示loading, empty, error的View
     */
    @Override
    public View getReplaceView() {
        return getSmartRefreshLayout();
    }

    @Override
    public void init(View view, Bundle savedInstanceState) {
        getRecyclerView().setLayoutManager(getLayoutManager());
        getRecyclerView().setAdapter(adapter);
        getSmartRefreshLayout().setEnabled(refreshEnable);
        getSmartRefreshLayout().setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadPageNum = 1;
                loadPageListData(1);
            }
        });
        _init(view, savedInstanceState);
        if (loadMoreEnable) {
            getSmartRefreshLayout().setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    loadPageNum = pageNum + 1;
                    loadPageListData(loadPageNum);
                }
            });
        }
    }

    @Override
    public void showListData(List<D> datas, int pageNum) {
        this.pageNum = pageNum;
        if (refreshEnable) {
            getSmartRefreshLayout().setEnabled(true);
        }
        adapter.setEnableLoadMore(true);
        if (pageNum == 1) {
            getSmartRefreshLayout().finishRefresh();
            adapter.setNewData(datas);
        } else {
            getSmartRefreshLayout().finishLoadMore();
            adapter.addData(datas);
        }
    }

    public void autoRefresh() {
        if (ListUtil.getCount(adapter.getData()) > 0) {
            getSmartRefreshLayout().autoRefresh();
        } else {
            showLoading();
            loadPageListData(1);
        }
    }

    public void showNoMore() {
        //加载更多---没有更多了
        if (refreshEnable) {
            getSmartRefreshLayout().setEnabled(true);
        }
        getSmartRefreshLayout().finishLoadMoreWithNoMoreData();
    }

    public void showLoadMoreError() {
        //加载更多--当前page发生网络错误
        if (refreshEnable) {
            getSmartRefreshLayout().setEnabled(true);
        }
        getSmartRefreshLayout().finishLoadMore(false);
    }

    @Override
    public void showNetworkError(View.OnClickListener listener) {
        showNetworkError("网络状态异常，请刷新重试", listener);
    }

    @Override
    public void showNetworkError(String msg, View.OnClickListener listener) {
        if (loadPageNum > 1) {
            showLoadMoreError();
        } else {
            pageNum = 1;
            if (refreshEnable) {
                getSmartRefreshLayout().setEnabled(true);
            }
            getSmartRefreshLayout().finishRefresh();
            adapter.getData().clear();
            super.showNetworkError(msg, listener);
        }
    }

    @Override
    public void showEmpty(String msg) {
        //加载当前page 出现List 为empty的情况--需要判断是否是第一页或者不是第一页
        showEmpty(msg, null);
    }

    @Override
    public void showEmpty(String content, View.OnClickListener clickListener) {
        //加载当前page 出现List 为empty的情况--需要判断是否是第一页或者不是第一页
        if (loadPageNum > 1) {
            showNoMore();
        } else {
            pageNum = 1;
            if (refreshEnable) {
                getSmartRefreshLayout().setEnabled(true);
            }
            getSmartRefreshLayout().finishRefresh();
            adapter.getData().clear();
            super.showEmpty(content, clickListener);
        }
    }

    @Override
    public void refreshComplete() {
        adapter.setEnableLoadMore(true);
        getSmartRefreshLayout().finishRefresh();
    }

}
