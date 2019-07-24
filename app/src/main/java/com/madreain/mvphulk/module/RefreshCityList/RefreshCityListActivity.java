package com.madreain.mvphulk.module.RefreshCityList;

import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.madreain.hulk.ui.BaseListActivity;
import com.madreain.mvphulk.consts.ARouterUri;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import com.madreain.mvphulk.R;

import butterknife.BindView;

/**
 * @author madreain
 * @date module：
 * description：
 */
@Route(path = ARouterUri.RefreshCityListActivity)
public class RefreshCityListActivity extends BaseListActivity<RefreshCityListPresenter, RefreshCityListAdapter<RefreshCityListActivity>, RefreshCityListListData> implements RefreshCityListContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_refresh_city_list;
    }

    @Override
    public void _init(Bundle savedInstanceState) {
        setSupportActionBarWithBack(toolbar);
        toolbar.setTitle("list展示只能刷新");
        setLoadMoreEnable(false);
        presenter.onStart();
    }

    @Override
    public void loadPageListData(int pageNo) {
        presenter.loadPageListData(pageNo);
    }

    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }


    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public View getReplaceView() {
        return smartRefreshLayout;
    }

}
