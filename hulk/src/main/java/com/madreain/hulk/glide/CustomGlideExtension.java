package com.madreain.hulk.glide;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.madreain.hulk.config.HulkConfig;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：为了添加新的方法，修改已有的方法或者添加对其他类型格式的支持，你需要在扩展中使用加了注解的静态方法。
 *              GlideOption用来添加自定义的方法，GlideType用来支持新的格式。
 *
 */

@GlideExtension
public class CustomGlideExtension {

    private CustomGlideExtension() {

    }

    /**
     * 1.自己新增的方法的第一个参数必须是RequestOptions options
     * 2.方法必须是静态的
     *
     * @param options RequestOptions
     */
    @SuppressLint("CheckResult")
    @GlideOption
    public static BaseRequestOptions<?> hulkDefault(BaseRequestOptions<?> options) {
       return options.centerCrop()
                .dontAnimate()
                .disallowHardwareConfig()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .format(DecodeFormat.PREFER_RGB_565)
                .placeholder(HulkConfig.getGlidePlaceHolder())
                .error(HulkConfig.getGlidePlaceHolder());
    }

    @SuppressLint("CheckResult")
    @GlideOption
    public static BaseRequestOptions<?> hulkErrorDrawable(BaseRequestOptions<?> options, @Nullable Drawable placeholder) {
        return options.centerCrop()
                .dontAnimate()
                .disallowHardwareConfig()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .format(DecodeFormat.PREFER_RGB_565)
                .placeholder(placeholder)
                .error(placeholder);
    }

}
