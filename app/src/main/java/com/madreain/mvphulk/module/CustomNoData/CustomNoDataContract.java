package com.madreain.mvphulk.module.CustomNoData;

import com.madreain.hulk.mvp.BaseRes;
import com.madreain.hulk.mvp.IModel;
import com.madreain.hulk.mvp.IView;
import com.madreain.mvphulk.module.SearchCity.SearchCityData;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @author madreain
 * @date module：
 * description：
 */
public interface CustomNoDataContract {
    interface View extends IView {
        String getValue();
    }

    interface Model extends IModel {
        Flowable<BaseRes<List<CustomNoDataData>>> searchCustomCity(String value);

    }
}
