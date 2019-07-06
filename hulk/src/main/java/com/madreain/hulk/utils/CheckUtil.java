package com.madreain.hulk.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */

public class CheckUtil {
    /**
     * 手机号码格式判断，
     *
     * @param mobile
     * @return
     */

    public static boolean checkMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        if (mobile.length() == 11) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 邮箱格式判断，
     *
     * @param email
     * @return
     */

    public static boolean checkEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        Pattern p = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 字符串null转""
     *
     * @param src
     * @return
     */
    public static String notNull(String src) {
        if (src == null)
            return "";
        return src;
    }
}
