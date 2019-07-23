package com.madreain.mvphulk.module.My;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.madreain.hulk.ui.BaseFragment;
import com.madreain.mvphulk.R;

import butterknife.BindView;

/**
 * @author madreain
 * @date module：
 * description：
 */
public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View {

    @BindView(R.id.fragment_my)
    RelativeLayout prelativeLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }


    @Override
    public void init(View view, Bundle savedInstanceState) {
        //初始化
        presenter.onStart();
    }

    @Override
    public View getReplaceView() {
        return prelativeLayout;
    }
}
