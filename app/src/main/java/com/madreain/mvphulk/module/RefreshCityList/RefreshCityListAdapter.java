package com.madreain.mvphulk.module.RefreshCityList;


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
public class RefreshCityListAdapter<V extends IView> extends BaseAdapter<RefreshCityListListData, RefreshCityListActivity> {

    @Inject
    public RefreshCityListAdapter() {
        super(R.layout.item_activity_refresh_city_list, new ArrayList<RefreshCityListListData>());
    }

    @Override
    protected void convert(HulkViewHolder helper, RefreshCityListListData data) {
        helper.setText(R.id.tv, data.getName());
    }
}
