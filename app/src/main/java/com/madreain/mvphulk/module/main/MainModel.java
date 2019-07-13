package com.madreain.mvphulk.module.main;


import com.madreain.hulk.mvp.BaseModel;
import com.madreain.mvphulk.module.api.ApiService;

import javax.inject.Inject;


/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */
public class MainModel extends BaseModel<ApiService> implements MainContract.Model {

    @Inject
    public MainModel() {

    }

}
