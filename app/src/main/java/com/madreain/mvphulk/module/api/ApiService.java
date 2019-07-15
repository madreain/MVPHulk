package com.madreain.mvphulk.module.api;

import com.madreain.hulk.mvp.BaseRes;
import com.madreain.mvphulk.module.CityList.CityListListData;
import com.madreain.mvphulk.module.Custom.CustomListData;
import com.madreain.mvphulk.module.CustomNoData.CustomNoDataData;
import com.madreain.mvphulk.module.RefreshCityList.RefreshCityListListData;
import com.madreain.mvphulk.module.SearchCity.SearchCityData;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author madreain
 * @date 2019-07-06.
 * module：接口
 * description： 这里用的接口来自https://github.com/MZCretin/RollToolsApi，感谢MZCretin
 */
public interface ApiService {

    @GET("api/address/list")
    Flowable<BaseRes<List<CityListListData>>> getCityList();

    @GET("api/address/list")
    Flowable<BaseRes<List<RefreshCityListListData>>> getRefreshCityList();

    @GET("api/address/search?type=0&")
    Flowable<BaseRes<List<SearchCityData>>> searchCity(@Query("value") String value);

    @GET("api/address/list")
    Flowable<BaseRes<List<CustomListData>>> getCCityList();

    @GET("api/address/search?type=0&")
    Flowable<BaseRes<List<CustomNoDataData>>> searchCustomCity(@Query("value") String value);

}
