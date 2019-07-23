package com.madreain.mvphulk.module.CustomRefresh;


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
public class CustomRefreshAdapter<V extends IView> extends BaseAdapter<CustomRefreshListData, CustomRefreshActivity> {

    @Inject
    public CustomRefreshAdapter() {
        super(R.layout.item_activity_custom_refresh, new ArrayList<CustomRefreshListData>());
    }

    @Override
    protected void convert(HulkViewHolder helper, CustomRefreshListData data) {
        helper.setText(R.id.tv, data.getName());
    }
}
