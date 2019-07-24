package com.madreain.mvphulk.module.CustomRefresh;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.madreain.hulk.ui.BaseListActivity;
import com.madreain.mvphulk.consts.ARouterUri;
import com.madreain.mvphulk.view.MyClassicsHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import com.madreain.mvphulk.R;

import butterknife.BindView;

/**
 * @author madreain
 * @date module： 自定义刷新加载，更多参考https://github.com/scwang90/SmartRefreshLayout中的Demo样式
 * description：
 */
@Route(path = ARouterUri.CustomRefreshActivity)
public class CustomRefreshActivity extends BaseListActivity<CustomRefreshPresenter, CustomRefreshAdapter<CustomRefreshActivity>, CustomRefreshListData> implements CustomRefreshContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_refresh;
    }


    @Override
    public void _init(Bundle savedInstanceState) {
        setSupportActionBarWithBack(toolbar);
        toolbar.setTitle("自定义刷新加载");
        //自定义刷新头
        smartRefreshLayout.setRefreshHeader(new MyClassicsHeader(this));
        smartRefreshLayout.setHeaderHeight(60);

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
