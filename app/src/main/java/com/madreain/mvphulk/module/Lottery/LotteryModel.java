package com.madreain.mvphulk.module.Lottery;

import com.madreain.mvphulk.module.api.ApiService;
import com.madreain.hulk.mvp.BaseRes;
import com.madreain.hulk.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */
public class LotteryModel extends BaseModel<ApiService> implements LotteryContract.Model {

    @Inject
    public LotteryModel() {
        super();
    }

    @Override
    public Flowable<BaseRes<List<LotteryListData>>> loadListDatas(int pageNo) {
        return apiService.getLotteryList(pageNo);//接口调用 apiService.xxx
    }
}
