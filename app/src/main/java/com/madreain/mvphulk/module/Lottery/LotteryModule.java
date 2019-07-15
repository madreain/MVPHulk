package com.madreain.mvphulk.module.Lottery;

import dagger.Module;
import dagger.Provides;

/**
 * @author madreain
 * @date 2019/2/20.
 * module：
 * description：
 */

@Module
public class LotteryModule {

    @Provides
    LotteryContract.View getView(LotteryActivity view) {
        return view;
    }


}
