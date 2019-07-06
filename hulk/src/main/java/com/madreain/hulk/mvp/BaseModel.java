package com.madreain.hulk.mvp;

import com.madreain.hulk.config.HulkConfig;

import java.lang.reflect.ParameterizedType;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */
public abstract class BaseModel<API> implements IModel {

    private API api;

    public API apiService = getApiService();

    @SuppressWarnings("unchecked")
    public API getApiService() {
        if (api == null) {
            api = HulkConfig.getRetrofit().create((Class<API>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        }
        return api;
    }

    @Override
    public void onDestroy() {

    }

}
