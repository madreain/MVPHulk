package com.madreain.mvphulk.module.Main;


import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */
@Module
public class MainModule {

    @Provides
    MainContract.View getView(MainActivity view) {
        return view;
    }

}
