package com.madreain.mvphulk.module.api;

import com.madreain.hulk.mvp.BaseRes;
import com.madreain.mvphulk.module.CityList.CityListListData;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author madreain
 * @date 2019-07-06.
 * module：接口
 * description：
 */
public interface ApiService {

    @GET("api//address/list")
    Flowable<BaseRes<List<CityListListData>>> getCityList();

}
