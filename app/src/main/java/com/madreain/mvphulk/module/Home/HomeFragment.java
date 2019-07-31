package com.madreain.mvphulk.module.Home;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.madreain.hulk.ui.BaseListFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import com.madreain.mvphulk.R;

import butterknife.BindView;

/**
 * @author madreain
 * @date module：
 * description：
 */
public class HomeFragment extends BaseListFragment<HomePresenter, HomeAdapter<HomeFragment>, HomeListData> implements HomeContract.View {

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void _init(View view, Bundle savedInstanceState) {
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
        return new LinearLayoutManager(getContext());
    }

    @Override
    public View getReplaceView() {
        return smartRefreshLayout;
    }

}
