package com.madreain.mvphulk;

import com.madreain.mvphulk.module.CityList.CityListActivity;
import com.madreain.mvphulk.module.CityList.CityListModule;
import com.madreain.mvphulk.module.RefreshCityList.RefreshCityListActivity;
import com.madreain.mvphulk.module.RefreshCityList.RefreshCityListModule;
import com.madreain.mvphulk.module.main.MainActivity;
import com.madreain.mvphulk.module.main.MainModule;

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

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = CityListModule.class)
    abstract CityListActivity cityListActivity();

    @ContributesAndroidInjector(modules = RefreshCityListModule.class)
    abstract RefreshCityListActivity refreshCityListActivity();

}
