package com.madreain.mvphulk.module.Custom;

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
public class CustomModel extends BaseModel<ApiService> implements CustomContract.Model {

    @Inject
    public CustomModel() {
        super();
    }

    @Override
    public Flowable<BaseRes<List<CustomListData>>> loadListDatas(int pageNo) {
        return apiService.getCCityList();//接口调用 apiService.xxx
    }
}
