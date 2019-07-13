package com.madreain.mvphulk.module.CityList;

import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */

@Module
public class CityListModule {

    @Provides
    CityListContract.View getView(CityListActivity view) {
        return view;
    }

}
