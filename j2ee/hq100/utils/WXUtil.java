package com.up72.hq.utils;

import com.up72.common.util.JsonUtil;
import com.up72.hq.conf.SystemConfig;
import com.up72.hq.constant.ThirdCnst;
import com.up72.hq.utils.http.HttpConnectionUtil;
import com.up72.hq.utils.third.dto.WXAccessTokenDto;
import com.up72.hq.utils.third.dto.WXUserInfoDto;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信登录工具类
 */
public class WXUtil {
    //安全校验
    public final static String state = "dahuaishu";
    //回调地址
    public final static String WX_BACKURL = SystemConfig.instants().getValue("WX_BACKURL");
    //获取token
    public static String WX_ACCESS_TOKEN_URL = SystemConfig.instants().getValue("WX_ACCESS_TOKEN_URL");
    //获取用户信息
    public static String WX_GET_USER_INFO_URL = SystemConfig.instants().getValue("WX_GET_USER_INFO_URL");

    /**
     * 获取微信用户的access_token和openId
     * @param code
     * @return
     */
    public static WXAccessTokenDto getACCESS_TOKENAndOpenId(String code){
        String result_access_token = null;
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("grant_type","authorization_code");
            map.put("code",code);
            map.put("appid", ThirdCnst.WX.WX_APPID);
            map.put("secret", ThirdCnst.WX.WX_APPKEY);
            result_access_token  =  HttpConnectionUtil.instants().send(HttpConnectionUtil.METHOD_GET, WXUtil.WX_ACCESS_TOKEN_URL,map);
            System.out.println("返回包result_access_tolen===="+result_access_token);
            WXAccessTokenDto wxAccessTokenDto = (WXAccessTokenDto) JsonUtil.json2Object(result_access_token,WXAccessTokenDto.class);
            return wxAccessTokenDto;
        }catch (Exception e){
            System.out.println("获取access_token接口报错::::"+result_access_token);
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取微信用户的信息
     * @param openId
     * @param access_token
     * @return
     */
    public static WXUserInfoDto getUserInfoDto(String openId,String access_token){
        String result_userInfo = null;
        WXUserInfoDto wxUserInfoDto = null;
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("access_token",access_token);
            map.put("openid",openId);
            result_userInfo  =  HttpConnectionUtil.instants().send(HttpConnectionUtil.METHOD_GET, WXUtil.WX_GET_USER_INFO_URL,map);
            System.out.println("返回包result_userInfo===="+result_userInfo);
            wxUserInfoDto = (WXUserInfoDto) JsonUtil.json2Object(result_userInfo, WXUserInfoDto.class);
        }catch (Exception e){
            System.out.println("获取用户信息接口报错::::"+result_userInfo);
            e.printStackTrace();
        }
        return wxUserInfoDto;
    }
}
