package com.madreain.mvphulk.module.Home;

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
public interface HomeContract {
    interface View extends IListView<HomeListData> {
    }

    interface Model extends IModel {
        Flowable<BaseRes<List<HomeListData>>> loadListDatas(int pageNo);
    }
}
