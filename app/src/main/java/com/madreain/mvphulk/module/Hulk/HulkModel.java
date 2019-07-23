package com.madreain.mvphulk.module.Hulk;

import com.madreain.mvphulk.module.api.ApiService;
import com.madreain.hulk.mvp.BaseModel;

import javax.inject.Inject;


/**
 * @author madreain
 * @date module：
 * description：
 */
public class HulkModel extends BaseModel<ApiService> implements HulkContract.Model {

    @Inject
    public HulkModel() {

    }

}
