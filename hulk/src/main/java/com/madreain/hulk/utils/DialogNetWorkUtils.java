package com.madreain.hulk.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;


/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */

public class DialogNetWorkUtils {

    private static AlertDialog alert;

    public static void showDialog(final Activity activity) {
        if ((alert != null && alert.isShowing())) {
            return;
        }
        if (!ActivityUtils.get().isTopActivity(activity)) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("网络已断开,是否设置网络？")
                .setCancelable(false)
                .setPositiveButton("设置", (dialog, id) -> intentNetWorkSetting(activity))
                .setNegativeButton("取消", (dialog, id) -> dialog.cancel());
        alert = builder.create();
        alert.show();
    }

    public static void dismissDialog() {
        if (alert != null && alert.isShowing()) {
            try {
                alert.dismiss();
            } catch (Exception ignored) {
            }
        }
    }

    //跳转设置wifi界面
    public static void intentNetWorkSetting(Activity activity) {
        try {
            Intent wifiSettingsIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            activity.startActivity(wifiSettingsIntent);
        } catch (Exception ignored) {
        }
    }
}
