package com.madreain.mvphulk.module.Common;


import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date module：
 * description：
 */
@Module
public class CommonModule {

    @Provides
    CommonContract.View getView(CommonDialogFragment view) {
        return view;
    }

}
