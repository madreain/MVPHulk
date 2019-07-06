package com.madreain.hulk.view.loadmore;

import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * @author madreain
 * @date 2019-07-05.
 * module：
 * description：对BaseRecyclerViewAdapterHelper的LoadMoreView增加是否显示底部loadview的参数
 */
public abstract class LoadMoreViewAbstract extends LoadMoreView {

    /**
     * 是否显示底部的的 loadMoreView
     */
    protected boolean isShow;

    /**
     * 是否显示loadMoreView
     * @param isShow
     */
    public abstract void setShowLoadMoreView(boolean isShow);

}
