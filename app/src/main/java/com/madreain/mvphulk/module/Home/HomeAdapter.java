package com.madreain.mvphulk.module.Home;


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
public class HomeAdapter<V extends IView> extends BaseAdapter<HomeListData, HomeFragment> {

    @Inject
    public HomeAdapter() {
        super(R.layout.item_fragment_home, new ArrayList<HomeListData>());
    }

    @Override
    protected void convert(HulkViewHolder helper, HomeListData data) {
        helper.setText(R.id.tv, "测试");
    }
}
