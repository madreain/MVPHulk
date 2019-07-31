package com.madreain.mvphulk.module.Custom;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.madreain.hulk.ui.BaseListActivity;
import com.madreain.hulk.view.varyview.IVaryViewHelperController;
import com.madreain.mvphulk.consts.ARouterUri;
import com.madreain.mvphulk.view.MyVaryViewHelperController;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import com.madreain.mvphulk.R;

import butterknife.BindView;

/**
 * @author madreain
 * @date module：
 * description：
 */
@Route(path = ARouterUri.CustomActivity)
public class CustomActivity extends BaseListActivity<CustomPresenter, CustomAdapter<CustomActivity>, CustomListData> implements CustomContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom;
    }


    @Override
    public void _init(Bundle savedInstanceState) {
        setSupportActionBarWithBack(toolbar);
        toolbar.setTitle("自定义View替换(断网查看网络异常情况)");
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

    /**
     * 自定义相关不同状态展示的view
     * @return
     */
    @Override
    protected IVaryViewHelperController initVaryViewHelperController() {
        return new MyVaryViewHelperController(getReplaceView());
    }

}
