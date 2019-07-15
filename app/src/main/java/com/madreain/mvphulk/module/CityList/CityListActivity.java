package com.madreain.mvphulk.module.CityList;

import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
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

@Route(path = ARouterUri.CityListActivity)
public class CityListActivity extends BaseListActivity<CityListPresenter, CityListAdapter<CityListActivity>, CityListListData> implements CityListContract.View {

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_city_list;
    }


    @Override
    public void _init(Bundle savedInstanceState) {
        presenter.onStart();
    }

    @Override
    public void loadPageListData(int pageNo) {
//        presenter.loadPageListData(pageNo);
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
