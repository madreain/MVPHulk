package com.madreain.mvphulk.module.CityList;


import com.madreain.hulk.adapter.BaseAdapter;
import com.madreain.hulk.view.baseviewholder.HulkViewHolder;

import com.madreain.mvphulk.R;


import java.util.ArrayList;

import javax.inject.Inject;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */
public class CityListAdapter extends BaseAdapter<CityListListData, CityListActivity> {

    @Inject
    public CityListAdapter() {
        super(R.layout.item_activity_city_list, new ArrayList<>());
    }

    @Override
    protected void convert(HulkViewHolder helper, CityListListData data) {
        helper.setText(R.id.tv, data.getName());
    }
}
