package com.madreain.mvphulk.module.Home;

import com.madreain.mvphulk.module.api.ApiService;
import com.madreain.hulk.mvp.BaseRes;
import com.madreain.hulk.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */
public class HomeModel extends BaseModel<ApiService> implements HomeContract.Model {

    @Inject
    public HomeModel() {
        super();
    }

    @Override
    public Flowable<BaseRes<List<HomeListData>>> loadListDatas(int pageNo) {
        return null;//接口调用 apiService.xxx
    }
}
