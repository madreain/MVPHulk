package com.madreain.hulk.utils;

import android.net.Uri;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：ARouter工具类封装
 * description：
 */

public class ARouterUtils {

    /**
     * Activity/Fragment类里面进行Arouter 注入
     * @param thiz
     */
    public static void inject(Object thiz) {
        ARouter.getInstance().inject(thiz);
    }

    /**
     * 设置跳转的路由
     * @param url
     * @return
     */
    public static Postcard build(String url) {
        return ARouter.getInstance().build(url);
    }

    /**
     * 设置跳转的路由
     * @param uri
     * @return
     */
    public static Postcard build(Uri uri) {
        return ARouter.getInstance().build(uri);
    }

    /**
     * 设置跳转的路由
     * @param path
     * @param group
     * @return
     */
    public Postcard build(String path, String group) {
        return ARouter.getInstance().build(path, group);
    }

    /**
     * 在Appilication的onTerminte进行销毁
     */
    public static void destory(){
        ARouter.getInstance().destroy();
    }
}
