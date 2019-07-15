package com.madreain.mvphulk.module.Lottery;


import com.madreain.hulk.adapter.BaseAdapter;
import com.madreain.hulk.view.baseviewholder.HulkViewHolder;
import com.madreain.hulk.mvp.IView;

import com.madreain.mvphulk.R;


import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */
public class LotteryAdapter<V extends IView> extends BaseAdapter<LotteryListData, LotteryActivity> {

    @Inject
    public LotteryAdapter() {
        super(R.layout.item_activity_lottery, new ArrayList<LotteryListData>());
    }

    @Override
    protected void convert(HulkViewHolder helper, LotteryListData data) {
        helper.setText(R.id.tv, "测试");
    }
}
