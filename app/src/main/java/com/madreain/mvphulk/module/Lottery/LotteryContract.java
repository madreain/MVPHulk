package com.madreain.mvphulk.module.Lottery;

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
public interface LotteryContract {
    interface View extends IListView<LotteryListData> {
    }

    interface Model extends IModel {
        Flowable<BaseRes<List<LotteryListData>>> loadListDatas(int pageNo);
    }
}
