package com.madreain.mvphulk.module.CustomRefresh;

import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */

@Module
public class CustomRefreshModule {

    @Provides
    CustomRefreshContract.View getView(CustomRefreshActivity view) {
        return view;
    }


}
