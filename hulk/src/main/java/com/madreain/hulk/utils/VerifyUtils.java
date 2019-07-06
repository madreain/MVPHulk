package com.madreain.hulk.utils;

import android.app.Activity;
import android.text.TextUtils;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：公共校验类
 */

public class VerifyUtils {

    private Activity activity;

    public VerifyUtils(Activity activity) {
        this.activity = activity;
    }

    /**
     * 校验手机号码
     *
     * @param mobileNo
     * @return true:验证失败
     */
    public static boolean isMobile(String mobileNo) {
        if (TextUtils.isEmpty(mobileNo)) {
            return false;
        }
        if (mobileNo.matches("^(1)\\d{10}$")) {
            return true;
        }
        return false;
    }

    /**
     * 校验身份证号码
     *
     * @param idCard
     * @return true:验证失败
     */
    public static boolean isIdCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return true;
        }
        if (!idCard.matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)")) {
            return true;
        }
        return false;
    }

    //验证密码8-32位，必须是字母数字组合，可以输入下划线
    public static boolean isPwdEffective(String pwd) {
        if (StringUtils.isEmpty(pwd)) {
            return false;
        }
        return pwd.matches("^(?![0-9_]+$)(?![a-zA-Z_]+$)[0-9A-Za-z_]{8,32}$");
//        return !TextUtils.isEmpty(pwd) && pwd.length() >= 8;
    }

    /**
     * 手机号中间4位 替换成*
     *
     * @param mobile
     * @return
     */
    public static String mobileReplace4Stars(String mobile) {
        if (isMobile(mobile)) {
            return mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
        }
        return mobile;
    }
}
