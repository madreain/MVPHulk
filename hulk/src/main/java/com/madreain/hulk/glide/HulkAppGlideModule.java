package com.madreain.hulk.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.madreain.hulk.config.HulkConfig;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：基本配置
 * description：相关配置的修改
 */
@GlideModule
public class HulkAppGlideModule extends AppGlideModule {


    /**
     * 禁止解析Manifest文件
     * 如果你的应用和其libraries都已经是Glide v4的AppGlideModule和LibraryGlideModule，你完全可以禁用manifest解析以提高Glide初始化速度并避免潜在的解析问题:
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return super.isManifestParsingEnabled();
    }

    /**
     * glide设置的相关配置
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .dontAnimate()
                .disallowHardwareConfig()
                .placeholder(HulkConfig.getGlidePlaceHolder())
                .error(HulkConfig.getGlidePlaceHolder())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .format(DecodeFormat.PREFER_RGB_565);
        builder.setDefaultRequestOptions(requestOptions);
    }

}
