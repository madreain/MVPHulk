package com.madreain.mvphulk.module.SearchCity;

import com.madreain.hulk.http.Transformer;
import com.madreain.hulk.http.resourceSubscriber.RSubscriber;
import com.madreain.hulk.mvp.BasePresenter;
import com.madreain.hulk.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;


/**
 * @author madreain
 * @date module：
 * description：
 */
public class SearchCityPresenter extends BasePresenter<SearchCityModel, SearchCityContract.View> {

    @Inject
    SearchCityPresenter() {

    }

    @Override
    public void onStart() {
        searchCity();
    }

    private void searchCity() {
        model.searchCity(view.getValue())
                .compose(Transformer.retrofit(view))
                .subscribeWith(new RSubscriber<List<SearchCityData>>(view, RSubscriber.REPLACE) {
                    @Override
                    public void _onNext(List<SearchCityData> datas) {
                        LogUtils.d("11111111", datas.toString());
                    }

                    @Override
                    public void retry() {
                        searchCity();
                    }
                });

    }

}