package com.madreain.mvphulk.module.Custom;

import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */

@Module
public class CustomModule {

    @Provides
    CustomContract.View getView(CustomActivity view) {
        return view;
    }


}
