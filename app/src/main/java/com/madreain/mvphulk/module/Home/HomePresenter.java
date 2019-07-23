package com.madreain.mvphulk.module.Home;

import com.madreain.hulk.mvp.BasePresenter;
import com.madreain.hulk.http.resourceSubscriber.RSubscriberList;
import com.madreain.hulk.http.Transformer;

import java.util.List;

import javax.inject.Inject;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */
public class HomePresenter extends BasePresenter<HomeModel, HomeContract.View> {

    @Inject
    HomePresenter() {

    }

    @Override
    public void onStart() {
        loadPageListData(1);
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    public void loadPageListData(final int pageNum) {
        model.loadListDatas(pageNum)
                .compose(Transformer.retrofit(view))
                .subscribeWith(new RSubscriberList<List<HomeListData>>(view, pageNum) {
                    @Override
                    public void _onNext(List<HomeListData> datas) {
                        view.showListData(datas, pageNum);
                    }

                    @Override
                    public void retry() {
                        loadPageListData(pageNum);
                    }

                });
    }
}
