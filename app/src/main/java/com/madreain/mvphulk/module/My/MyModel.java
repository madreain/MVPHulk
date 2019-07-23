package com.madreain.mvphulk.module.My;

import com.madreain.mvphulk.module.api.ApiService;
import com.madreain.hulk.mvp.BaseModel;

import javax.inject.Inject;


/**
 * @author madreain
 * @date module：
 * description：
 */
public class MyModel extends BaseModel<ApiService> implements MyContract.Model {

    @Inject
    public MyModel() {

    }

}
