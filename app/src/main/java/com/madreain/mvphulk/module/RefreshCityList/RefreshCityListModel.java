package com.madreain.mvphulk.module.RefreshCityList;

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
public class RefreshCityListModel extends BaseModel<ApiService> implements RefreshCityListContract.Model {

    @Inject
    public RefreshCityListModel() {
        super();
    }

    @Override
    public Flowable<BaseRes<List<RefreshCityListListData>>> loadListDatas(int pageNo) {
        return apiService.getRefreshCityList();//接口调用 apiService.xxx
    }
}
