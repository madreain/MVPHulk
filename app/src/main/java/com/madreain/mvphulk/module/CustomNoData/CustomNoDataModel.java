package com.madreain.mvphulk.module.CustomNoData;

import com.madreain.hulk.mvp.BaseRes;
import com.madreain.mvphulk.module.SearchCity.SearchCityData;
import com.madreain.mvphulk.module.api.ApiService;
import com.madreain.hulk.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;


/**
 * @author madreain
 * @date module：
 * description：
 */
public class CustomNoDataModel extends BaseModel<ApiService> implements CustomNoDataContract.Model {

    @Inject
    public CustomNoDataModel() {

    }

    @Override
    public Flowable<BaseRes<List<CustomNoDataData>>> searchCustomCity(String value) {
        return apiService.searchCustomCity(value);
    }
}
