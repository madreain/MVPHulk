package com.madreain.mvphulk.module.CustomNoData;

import com.madreain.hulk.http.Transformer;
import com.madreain.hulk.http.resourceSubscriber.RSubscriber;
import com.madreain.hulk.mvp.BasePresenter;
import com.madreain.hulk.utils.LogUtils;
import com.madreain.mvphulk.module.SearchCity.SearchCityData;

import java.util.List;

import javax.inject.Inject;


/**
 * @author madreain
 * @date module：
 * description：
 */
public class CustomNoDataPresenter extends BasePresenter<CustomNoDataModel, CustomNoDataContract.View> {

    @Inject
    CustomNoDataPresenter() {

    }

    @Override
    public void onStart() {
        searchCustomCity();
    }

    private void searchCustomCity() {
        model.searchCustomCity(view.getValue())
                .compose(Transformer.retrofit(view))
                .subscribeWith(new RSubscriber<List<CustomNoDataData>>(view, RSubscriber.REPLACE) {
                    @Override
                    public void _onNext(List<CustomNoDataData> datas) {
                        LogUtils.d("11111111", datas.toString());
                    }

                    @Override
                    public void retry() {
                        searchCustomCity();
                    }
                });

    }


}