package com.madreain.mvphulk.module.CustomRefresh;

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
public interface CustomRefreshContract {
    interface View extends IListView<CustomRefreshListData> {
    }

    interface Model extends IModel {
        Flowable<BaseRes<List<CustomRefreshListData>>> loadListDatas(int pageNo);
    }
}
