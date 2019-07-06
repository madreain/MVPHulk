package com.madreain.hulk.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.madreain.hulk.mvp.IView;
import com.madreain.hulk.view.baseviewholder.HulkViewHolder;
import com.madreain.hulk.view.loadmore.HulkLoadMoreView;

import java.util.List;

import javax.inject.Inject;


/**
 * @author madreain
 * @date 2019-07-05.
 * module：
 * description：
 */
public abstract class BaseMultiAdapter<T extends MultiItemEntity, V extends IView> extends BaseMultiItemQuickAdapter<T, HulkViewHolder> {
    private RecyclerView recyclerView;

    public abstract void addItemType();

    private HulkLoadMoreView loadMoreView;

    public BaseMultiAdapter(List<T> data) {
        super(data);
        addItemType();
    }

    @Override
    public void setLoadMoreView(LoadMoreView loadMoreView) {
        super.setLoadMoreView(loadMoreView);
        this.loadMoreView = (HulkLoadMoreView) loadMoreView;
    }

    public void setNewData(List<T> data) {
        super.setNewData(data);
        if (loadMoreView != null) {
            loadMoreView.setShowLoadMoreView(false);
            checkFullPage();
        }
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
                if ((linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1) != getItemCount()) {
                    loadMoreView.setShowLoadMoreView(true);
                }
            }, 50);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            recyclerView.postDelayed(() -> {
                final int[] positions = new int[2];
                staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(positions);
                int pos = Math.max(positions[0], positions[1]) + 1;
                if (pos != getItemCount()) {
                    loadMoreView.setShowLoadMoreView(true);
                }
            }, 50);
        }
    }

    public abstract void addClickListener();//添加各种点击事件，BaseListActivity中调用改方法，实现类中不用调用

    @Inject
    public V view;


//    private GlideRequests glide;
//
//    public Context getContext() {
//        return mContext;
//    }
//
//    public GlideRequests glide() {
//        if (glide != null) {
//            return glide;
//        }
//        if (view instanceof android.support.v4.app.Fragment) {
//            glide = GlideApp.with((android.support.v4.app.Fragment) view);
//        } else if (view instanceof Fragment) {
//            glide = GlideApp.with((Fragment) view);
//        } else if (view instanceof FragmentActivity) {
//            glide = GlideApp.with((FragmentActivity) view);
//        } else if (view instanceof Activity) {
//            glide = GlideApp.with((Activity) view);
//        } else if (view instanceof Context) {
//            glide = GlideApp.with((Context) view);
//        } else if (view instanceof View) {
//            glide = GlideApp.with((View) view);
//        } else {
//            glide = GlideApp.with(getContext());
//        }
//        return glide;
//    }
//
//    public GlideRequest<Drawable> loadImageDefaultConfig(String url) {
//        return glide().load(OSSPathUtils.getOSSPath(url)).gjDefaultConfig();
//    }
//
//    public GlideRequest<Drawable> loadImage(String url) {
//        return glide().load(OSSPathUtils.getOSSPath(url));
//    }
//
}
