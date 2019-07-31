package com.madreain.mvphulk.application;

import com.madreain.hulk.application.ApiModule;
import com.madreain.hulk.application.IAppComponent;
import com.madreain.mvphulk.BuilderModule;

import javax.inject.Singleton;

import dagger.Component;
//import dagger.android.AndroidInjectionModule;
import retrofit2.Retrofit;

/**
 * @author madreain
 * @date 2019-07-06.
 * module：
 * description：
 */
@Singleton
@Component(modules = {ApiModule.class, BuilderModule.class})
public interface Appcomponent extends IAppComponent {

    void inject(MVPHulkApplication mvpHulkApplication);

    Retrofit getRetrofit();

}
