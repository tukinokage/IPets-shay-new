package com.shay.baselibrary;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class ObjectTransformUtil {
    public static Map<String, Object> objectToMap(Object o){

        Gson gson = new Gson();
        String json = gson.toJson(o);
        HashMap<String, Object> params = gson.fromJson(json, new TypeToken<HashMap<String, Object>>(){}.getType());
        return params;

    }
}
