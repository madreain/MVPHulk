package com.madreain.mvphulk.module.Custom;


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
public class CustomAdapter<V extends IView> extends BaseAdapter<CustomListData, CustomActivity> {

    @Inject
    public CustomAdapter() {
        super(R.layout.item_activity_custom, new ArrayList<CustomListData>());
    }

    @Override
    protected void convert(HulkViewHolder helper, CustomListData data) {
        helper.setText(R.id.tv, data.getName());
    }
}
