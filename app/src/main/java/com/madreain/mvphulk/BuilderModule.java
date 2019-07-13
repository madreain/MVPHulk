package com.madreain.mvphulk;

import com.madreain.mvphulk.module.CityList.CityListActivity;
import com.madreain.mvphulk.module.CityList.CityListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author madreain
 * @date 2019-07-06.
 * module：
 * description：
 */
@Module
public abstract class BuilderModule {

    @ContributesAndroidInjector(modules = CityListModule.class)
    abstract CityListActivity cityListActivity();

}
