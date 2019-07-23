package com.madreain.mvphulk.module.CustomRefresh;

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
public class CustomRefreshModel extends BaseModel<ApiService> implements CustomRefreshContract.Model {

    @Inject
    public CustomRefreshModel() {
        super();
    }

    @Override
    public Flowable<BaseRes<List<CustomRefreshListData>>> loadListDatas(int pageNo) {
        return apiService.getCustomRefreshCityList();//接口调用 apiService.xxx
    }
}
