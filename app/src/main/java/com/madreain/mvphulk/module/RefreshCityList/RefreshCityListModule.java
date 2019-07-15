package com.madreain.mvphulk.module.RefreshCityList;

import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */

@Module
public class RefreshCityListModule {

    @Provides
    RefreshCityListContract.View getView(RefreshCityListActivity view) {
        return view;
    }


}
