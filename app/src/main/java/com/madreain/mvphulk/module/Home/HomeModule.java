package com.madreain.mvphulk.module.Home;

import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */

@Module
public class HomeModule {

    @Provides
    HomeContract.View getView(HomeFragment view) {
        return view;
    }


}
