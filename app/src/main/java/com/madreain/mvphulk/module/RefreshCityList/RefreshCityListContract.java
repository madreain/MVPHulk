package com.madreain.mvphulk.module.RefreshCityList;

import com.madreain.hulk.mvp.BaseRes;
import com.madreain.hulk.mvp.IModel;
import com.madreain.hulk.mvp.IListView;


import java.util.List;

import io.reactivex.Flowable;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */
public interface RefreshCityListContract {
    interface View extends IListView<RefreshCityListListData> {
    }

    interface Model extends IModel {
        Flowable<BaseRes<List<RefreshCityListListData>>> loadListDatas(int pageNo);
    }
}
