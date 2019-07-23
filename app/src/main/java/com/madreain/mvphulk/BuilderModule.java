package com.madreain.mvphulk;

import com.madreain.mvphulk.module.CityList.CityListActivity;
import com.madreain.mvphulk.module.CityList.CityListModule;
import com.madreain.mvphulk.module.Custom.CustomActivity;
import com.madreain.mvphulk.module.Custom.CustomModule;
import com.madreain.mvphulk.module.CustomNoData.CustomNoDataActivity;
import com.madreain.mvphulk.module.CustomNoData.CustomNoDataModule;
import com.madreain.mvphulk.module.RefreshCityList.RefreshCityListActivity;
import com.madreain.mvphulk.module.RefreshCityList.RefreshCityListModule;
import com.madreain.mvphulk.module.SearchCity.SearchCityActivity;
import com.madreain.mvphulk.module.SearchCity.SearchCityModule;
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

    @ContributesAndroidInjector(modules = SearchCityModule.class)
    abstract SearchCityActivity searchCityActivity();

    @ContributesAndroidInjector(modules = CustomModule.class)
    abstract CustomActivity customActivity();

    @ContributesAndroidInjector(modules = CustomNoDataModule.class)
    abstract CustomNoDataActivity customNoDataActivity();

}
