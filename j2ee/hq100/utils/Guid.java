package com.up72.hq.utils;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.util.UUID;

public class Guid {
    public static String app_key;
    /**
     * @description:随机获取key值
     * @return
     */
    public static String guid() {
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString();
        return key;
    }
    /**
     * 这是其中一个url的参数，是GUID的，全球唯一标志符
     * @return
     */
    public static String App_key() {
        Guid g = new Guid();
        String guid = g.guid();
        app_key = guid;
        return app_key.replaceAll("\\-","");
    }
    /**
     * 根据md5加密
     * @return
     */
    public static String App_screct() {
        String mw = "key" + app_key ;
        String app_sign = MD5Util.toMD5(mw).toUpperCase();// 得到以后还要用MD5加密。
        return app_sign;
    }

    public static void main(String[] args) {
        Guid gd = new Guid();
        String app_key=gd.App_key();
        System.out.println("app_key: "+app_key);
        String app_screct=gd.App_screct();
        System.out.println("app_screct: "+app_screct);
    }
}