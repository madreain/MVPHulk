package com.madreain.mvphulk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.madreain.hulk.utils.ARouterUtils;
import com.madreain.mvphulk.consts.ARouterUri;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                ARouterUtils.build(ARouterUri.CityListActivity).navigation();
                break;
            case R.id.tv2:
                ARouterUtils.build(ARouterUri.CityListActivity).navigation();
                break;
            case R.id.tv3:
                ARouterUtils.build(ARouterUri.CityListActivity).navigation();
                break;
            case R.id.tv4:
                ARouterUtils.build(ARouterUri.CityListActivity).navigation();
                break;
            case R.id.tv5:
                ARouterUtils.build(ARouterUri.CityListActivity).navigation();
                break;
        }
    }

}
