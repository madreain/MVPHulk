package com.madreain.mvphulk.module.SearchCity;


import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date module：
 * description：
 */
@Module
public class SearchCityModule {

    @Provides
    SearchCityContract.View getView(SearchCityActivity view) {
        return view;
    }

}
