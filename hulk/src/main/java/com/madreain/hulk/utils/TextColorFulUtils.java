package com.madreain.hulk.utils;

import android.support.annotation.ColorRes;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：TextView 多种颜色
 */

public class TextColorFulUtils {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        SpannableStringBuilder builder;

        public Builder() {
            builder = new SpannableStringBuilder();
        }

        public Builder append(String str, @ColorRes int colorRes) {
            return append(str, colorRes, true);
        }

        /**
         * @param str       文本
         * @param colorRes  颜色 R.color._000000
         * @param condition 满足条件true
         * @return
         */
        public Builder append(String str, @ColorRes int colorRes, boolean condition) {
            if (condition && !TextUtils.isEmpty(str)) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Utils.getContext().getResources().getColor(colorRes));
                int start = builder.length();
                int end = start + str.length();
                builder.append(str);
                builder.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return this;
        }

        /**
         * @param str       文本
         * @param colorRes  颜色 R.color._000000
         * @param condition 满足条件true
         * @param isBold    是否是粗体
         * @return
         */
        public Builder append(String str, @ColorRes int colorRes, boolean condition, boolean isBold) {
            if (condition && !TextUtils.isEmpty(str)) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Utils.getContext().getResources().getColor(colorRes));
                int start = builder.length();
                int end = start + str.length();
                builder.append(str);
                if (isBold) {
                    builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
                }
                builder.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return this;
        }

        public SpannableStringBuilder get() {
            return builder;
        }
    }
}
