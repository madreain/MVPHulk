package com.madreain.mvphulk.module.Hulk;


import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date module：
 * description：
 */
@Module
public class HulkModule {

    @Provides
    HulkContract.View getView(HulkFragment view) {
        return view;
    }

}
