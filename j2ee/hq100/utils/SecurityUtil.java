package com.up72.hq.utils;

import com.thoughtworks.paranamer.ParameterNamesNotFoundException;

import java.util.*;

/**
 * Created by Administrator on 2016/9/21.
 */
public class SecurityUtil {
    /**
     *
     *  @Description    : 身份验证token值算法：
     *                              算法是：将特定的某几个参数一map的数据结构传入，
     *                              进行字典序排序以后进行md5加密,32位小写加密；
     *  @Method_Name    : authentication
     *         请求传过来的token
     *  @param srcData   约定用来计算token的参数
     *  @return token
     */
    public static String authentication(Map<String , Object > srcData) {
        //排序，根据keyde 字典序排序
        if(null == srcData){
            throw new ParameterNamesNotFoundException("传入参数为空");
        }
        List<Map.Entry<String,Object>> list = new ArrayList<Map.Entry<String,Object>>(srcData.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Object>>(){
            //升序排序
            public int compare(Map.Entry<String,Object> o1, Map.Entry<String,Object> o2){
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuffer srcSb = new StringBuffer();
        for(Map.Entry<String , Object>srcAtom : list){
            srcSb.append(String.valueOf(srcAtom.getValue()));
        }
        //计算token
        String token = MD5Util.toMD5(srcSb.toString());
        return token;
    }
}
