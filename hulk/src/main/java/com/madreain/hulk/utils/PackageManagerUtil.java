package com.madreain.hulk.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */

public class PackageManagerUtil {
    private PackageManager mPackageManager;
    private List<String> mPackageNames = new ArrayList<>();
    private static final String GAODE_PACKAGE_NAME = "com.autonavi.minimap";
    private static final String BAIDU_PACKAGE_NAME = "com.baidu.BaiduMap";

    public static PackageManagerUtil getInstance() {
        return new PackageManagerUtil();
    }


    private void initPackageManager(Context context) {
        mPackageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = mPackageManager.getInstalledPackages(0);

        mPackageNames.clear();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                mPackageNames.add(packageInfos.get(i).packageName);
            }
        }
    }

    public boolean haveGaodeMap(Context context) {
        initPackageManager(context);
        return mPackageNames.contains(GAODE_PACKAGE_NAME);
    }

    public boolean haveBaiduMap(Context context) {
        initPackageManager(context);
        return mPackageNames.contains(BAIDU_PACKAGE_NAME);
    }
}
