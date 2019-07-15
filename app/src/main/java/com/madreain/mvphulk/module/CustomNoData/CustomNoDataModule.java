package com.madreain.mvphulk.module.CustomNoData;


import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date module：
 * description：
 */
@Module
public class CustomNoDataModule {

    @Provides
    CustomNoDataContract.View getView(CustomNoDataActivity view) {
        return view;
    }

}
