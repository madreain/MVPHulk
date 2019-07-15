package com.madreain.mvphulk.module.SearchCity;

import com.madreain.hulk.mvp.BaseRes;
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
public class SearchCityModel extends BaseModel<ApiService> implements SearchCityContract.Model {

    @Inject
    public SearchCityModel() {

    }

    @Override
    public Flowable<BaseRes<List<SearchCityData>>> searchCity(String value) {
        return apiService.searchCity(value);
    }


}
