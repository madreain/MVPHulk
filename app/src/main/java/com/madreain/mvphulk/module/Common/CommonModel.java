package com.madreain.mvphulk.module.Common;

import com.madreain.mvphulk.module.api.ApiService;
import com.madreain.hulk.mvp.BaseModel;

import javax.inject.Inject;


/**
 * @author madreain
 * @date module：
 * description：
 */
public class CommonModel extends BaseModel<ApiService> implements CommonContract.Model {

    @Inject
    public CommonModel() {

    }

}
