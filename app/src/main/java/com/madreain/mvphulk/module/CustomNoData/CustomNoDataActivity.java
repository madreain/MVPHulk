package com.madreain.mvphulk.module.CustomNoData;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.madreain.hulk.ui.BaseActivity;
import com.madreain.hulk.view.varyview.IVaryViewHelperController;
import com.madreain.mvphulk.R;
import com.madreain.mvphulk.consts.ARouterUri;
import com.madreain.mvphulk.view.MyVaryViewHelperController;

import butterknife.BindView;

/**
 * @author madreain
 * @date module：
 * description：
 */
@Route(path = ARouterUri.CustomNoDataActivity)
public class CustomNoDataActivity extends BaseActivity<CustomNoDataPresenter> implements CustomNoDataContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_custom_no_data)
    LinearLayout prelativeLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_no_data;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        setSupportActionBarWithBack(toolbar);
        toolbar.setTitle("自定义View替换(暂无数据)");
        //初始化
        presenter.onStart();
    }

    @Override
    public View getReplaceView() {
        return prelativeLayout;
    }

    @Override
    public String getValue() {
        return "广德";
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
