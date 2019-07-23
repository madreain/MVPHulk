package com.madreain.mvphulk.module.My;


import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date module：
 * description：
 */
@Module
public class MyModule {

    @Provides
    MyContract.View getView(MyFragment view) {
        return view;
    }

}
