package com.madreain.mvphulk.module.CityList;

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
public class CityListModel extends BaseModel<ApiService> implements CityListContract.Model {

    @Inject
    public CityListModel() {
    }

    @Override
    public Flowable<BaseRes<List<CityListListData>>> loadListDatas(int pageNo) {
        return apiService.getCityList();//接口调用 apiService.xxx
    }
}
