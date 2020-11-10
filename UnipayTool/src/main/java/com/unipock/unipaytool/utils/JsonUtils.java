package com.unipock.unipaytool.utils;

import com.google.gson.Gson;

public class JsonUtils {

    private JsonUtils() {
    }

    public static String encode(Object map) {
        return new Gson().toJson(map);
    }

    public static <T> T decode(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json,classOfT);
//        return gson.fromJson(json,new TypeToken<Class<T>>() {}.getType());
    }



}
