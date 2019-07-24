package com.madreain.mvphulk.module.Hulk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madreain.hulk.ui.BaseFragment;
import com.madreain.hulk.utils.ARouterUtils;
import com.madreain.mvphulk.R;
import com.madreain.mvphulk.consts.ARouterKey;
import com.madreain.mvphulk.consts.ARouterUri;
import com.madreain.mvphulk.module.Common.CommonDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author madreain
 * @date module：
 * description：
 */
public class HulkFragment extends BaseFragment<HulkPresenter> implements HulkContract.View {

    @BindView(R.id.fragment_hulk)
    LinearLayout prelativeLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hulk;
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

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8})
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
                ARouterUtils.build(ARouterUri.CustomRefreshActivity).navigation();
                break;
            case R.id.tv8:
                CommonDialogFragment commonDialog = new CommonDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ARouterKey.CommonTitle, "提示标题");
                bundle.putString(ARouterKey.CommonDesc, "提示内容");
                bundle.putString(ARouterKey.CommonLeft, "取消");
                bundle.putString(ARouterKey.CommonRight, "确定");
                commonDialog.setArguments(bundle);
                commonDialog.setOnLeftRightClickListener(new CommonDialogFragment.onLeftRightClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {

                    }
                });
                commonDialog.show(getChildFragmentManager(), CommonDialogFragment.class.getName() + "");
                break;
            default:
                break;
        }
    }
}
