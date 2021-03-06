package com.madreain.mvphulk.module.SearchCity;

import com.madreain.hulk.mvp.BaseRes;
import com.madreain.hulk.mvp.IModel;
import com.madreain.hulk.mvp.IView;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author madreain
 * @date module：
 * description：
 */
public interface SearchCityContract {
    interface View extends IView {
        String getValue();
    }

    interface Model extends IModel {
        Flowable<BaseRes<List<SearchCityData>>> searchCity(String value);
    }
}
