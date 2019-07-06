package com.madreain.hulk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.madreain.hulk.view.loadmore.HulkLoadMoreView;

import java.util.List;

/**
 * @author madreain
 * @date 2019-07-05.
 * module：
 * description：
 */
public abstract class BaseLibAdapter<T, VH extends BaseViewHolder> extends BaseQuickAdapter<T, VH> {

    public BaseLibAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    private RecyclerView recyclerView;
    private HulkLoadMoreView loadMoreView;

    public Context getContext() {
        return mContext;
    }

    @Override
    public void loadMoreEnd() {
        super.loadMoreEnd();
    }

    public void setNewData(List<T> data) {
        super.setNewData(data);
        if (loadMoreView != null) {
            loadMoreView.setShowLoadMoreView(false);
            checkFullPage();
        }
    }

    @Override
    public void setLoadMoreView(LoadMoreView loadMoreView) {
        super.setLoadMoreView(loadMoreView);
        this.loadMoreView = (HulkLoadMoreView) loadMoreView;
    }

    @Override
    public void addData(@NonNull T data) {
        super.addData(data);
        if (loadMoreView != null) {
            loadMoreView.setShowLoadMoreView(false);
            checkFullPage();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    public abstract void addClickListener();//添加各种点击事件，BaseListActivity中调用改方法，实现类中不用调用

    public void clearData() {
        getData().clear();
        notifyDataSetChanged();
    }

    /**
     * 检测是否满一屏，如果满一屏，将显示load more view
     */
    public void checkFullPage() {
        if (recyclerView == null) return;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null) return;
        if (manager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            recyclerView.postDelayed(() -> {
                if (linearLayoutManager.findLastVisibleItemPosition() != getItemCount()) {
                    loadMoreView.setShowLoadMoreView(true);
                }
            }, 50);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            recyclerView.postDelayed(() -> {
                final int[] positions = new int[2];
                staggeredGridLayoutManager.findLastVisibleItemPositions(positions);
                int pos = Math.max(positions[0], positions[1]) + 1;
                if (pos != getItemCount()) {
                    loadMoreView.setShowLoadMoreView(true);
                }
            }, 50);
        }
    }
}