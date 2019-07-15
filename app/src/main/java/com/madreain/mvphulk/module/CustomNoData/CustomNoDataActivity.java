package com.madreain.mvphulk.module.CustomNoData;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.madreain.hulk.ui.BaseActivity;
import com.madreain.hulk.view.varyview.IVaryViewHelperController;
import com.madreain.mvphulk.R;
import com.madreain.mvphulk.consts.ARouterUri;
import com.madreain.mvphulk.view.MyVaryViewHelperController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author madreain
 * @date module：
 * description：
 */
@Route(path = ARouterUri.CustomNoDataActivity)
public class CustomNoDataActivity extends BaseActivity<CustomNoDataPresenter> implements CustomNoDataContract.View {

    @BindView(R.id.activity_custom_no_data)
    RelativeLayout prelativeLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_no_data;
    }


    @Override
    public void init(Bundle savedInstanceState) {
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
