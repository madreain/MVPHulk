package com.madreain.mvphulk.module.main;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.madreain.hulk.ui.BaseActivity;
import com.madreain.hulk.utils.ARouterUtils;
import com.madreain.mvphulk.R;
import com.madreain.mvphulk.consts.ARouterUri;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author madreain
 * @date 2019-07-15.
 * module：
 * description：
 */

@Route(path = ARouterUri.MainActivity)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;
    @BindView(R.id.tv7)
    TextView tv7;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public View getReplaceView() {
        return null;
    }


    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                ARouterUtils.build(ARouterUri.CityListActivity).navigation();
                break;
            case R.id.tv2:
                ARouterUtils.build(ARouterUri.RefreshCityListActivity).navigation();
                break;
            case R.id.tv3:
                ARouterUtils.build(ARouterUri.SearchCityActivity).navigation();
                break;
            case R.id.tv4:
                ARouterUtils.build(ARouterUri.CityListActivity).navigation();
                break;
            case R.id.tv5:
                ARouterUtils.build(ARouterUri.CustomActivity).navigation();
                break;
            case R.id.tv6:
                ARouterUtils.build(ARouterUri.CustomNoDataActivity).navigation();
                break;
            case R.id.tv7:
                ARouterUtils.build(ARouterUri.CustomNoDataActivity).navigation();
                break;
            default:
                break;
        }
    }


}
