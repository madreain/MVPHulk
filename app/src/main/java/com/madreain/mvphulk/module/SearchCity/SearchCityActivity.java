package com.madreain.mvphulk.module.SearchCity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.madreain.hulk.ui.BaseActivity;
import com.madreain.mvphulk.R;
import com.madreain.mvphulk.consts.ARouterUri;

import butterknife.BindView;

/**
 * @author madreain
 * @date module：
 * description：
 */
@Route(path = ARouterUri.SearchCityActivity)
public class SearchCityActivity extends BaseActivity<SearchCityPresenter> implements SearchCityContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_search_city)
    LinearLayout activitySearchCity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_city;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        setSupportActionBarWithBack(toolbar);
        toolbar.setTitle("暂无数据");
        //初始化
        presenter.onStart();
    }

    @Override
    public View getReplaceView() {
        return activitySearchCity;
    }

    @Override
    public String getValue() {
        return "广德";
    }

}
