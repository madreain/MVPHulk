package com.madreain.hulk.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author madreain
 * @date 2019-07-04.
 * module：
 * description：GSON解析工具
 */

public class GsonUtils {

    private static GsonUtils gsonUtils;
    private Gson gson;

    private GsonUtils() {
        gson = new GsonBuilder().disableHtmlEscaping().create();
//        .disableHtmlEscaping()
    }

    public static GsonUtils getInstance() {
        if (gsonUtils == null) {
            gsonUtils = new GsonUtils();
        }
        return gsonUtils;
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public <D> D getObject(String json, Class<D> d) {
        return gson.fromJson(json, d);
    }

    public <T> List<T> getListObject(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    public List gsonToList(String gsonString, Object object) {
        List<Object> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<Object>>() {
            }.getType());
        }
        return list;
    }

    public Map<String, String> getMapString(String json) {
        return gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    public Map<String, String> getMapString(Object object) {
        return gson.fromJson(toJson(object), new TypeToken<Map<String, String>>() {
        }.getType());
    }
}
