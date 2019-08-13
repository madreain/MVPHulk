package com.madreain.hulk.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.madreain.hulk.config.HulkConfig;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：
 */
@Module
public class ApiModule {
    @Singleton
    @Provides
    Retrofit getRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(HulkConfig.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).setPrettyPrinting().disableHtmlEscaping().create()))//服务器数据的解析
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//call适配器
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient getOkHttpClient(List<Interceptor> interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //拦截器
        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }
        return builder.connectTimeout(HulkConfig.getConnectTimeout(), TimeUnit.SECONDS)//设置请求超时时间
                .readTimeout(HulkConfig.getReadTimeout(), TimeUnit.SECONDS)//设置读超时时间
                .writeTimeout(HulkConfig.getWriteTimeout(), TimeUnit.SECONDS)//设置写超时时间
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
    }


    private class NullStringToEmptyAdapterFactory implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    //对String类型 返回为null  解析为""
    private static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    @Singleton
    @Provides
    List<Interceptor> getInterceptors() {
        return HulkConfig.getOkHttpInterceptors();
    }

}
