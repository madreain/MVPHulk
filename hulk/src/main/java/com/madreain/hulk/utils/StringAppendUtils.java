package com.madreain.hulk.utils;

import android.text.TextUtils;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：字符串拼接，过滤null
 */

public class StringAppendUtils {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String append = "";

        public Builder append(String str) {
            if (!TextUtils.isEmpty(str)) {
                append += str;
            }
            return this;
        }

        public Builder append(String str, boolean condition) {
            if (!TextUtils.isEmpty(str) && condition) {
                append += str;
            }
            return this;
        }

        public String build() {
            return append;
        }
    }
}
