package com.madreain.mvphulk.application;

import android.app.Application;

import dagger.Module;

/**
 * @author madreain
 * @date 2019-07-06.
 * module：
 * description：
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

}
