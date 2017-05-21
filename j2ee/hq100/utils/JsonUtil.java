package com.up72.hq.utils;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
public class JsonUtil extends com.up72.common.util.JsonUtil{
    public static String list2json(List<?> list) {
        Gson gson = new Gson();
        StringBuffer json = new StringBuffer();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(gson.toJson(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }
}
