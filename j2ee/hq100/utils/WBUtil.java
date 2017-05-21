package com.up72.hq.utils;

import com.up72.common.util.JsonUtil;
import com.up72.hq.conf.SystemConfig;
import com.up72.hq.constant.ThirdCnst;
import com.up72.hq.utils.http.HttpConnectionUtil;
import com.up72.hq.utils.third.dto.WBAccessTokenDto;
import com.up72.hq.utils.third.dto.WBUserInfoDto;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 微博登录工具类
 */
public class WBUtil {
    //回调地址
    public final static String WB_BACKURL = SystemConfig.instants().getValue("WB_BACKURL");
    //获取token
    public static String WB_ACCESS_TOKEN_URL = SystemConfig.instants().getValue("WB_ACCESS_TOKEN_URL");
    //获取用户信息
    public static String WB_GET_USER_INFO = SystemConfig.instants().getValue("WB_GET_USER_INFO");
    /**
     * 获取wb的access_token  和  uid
     * @param code
     * @return
     */
    public static WBAccessTokenDto getAccessTokenAndUid(String code){
        WBAccessTokenDto wbAccessTokenDto = null;
        String result_access_token = null;
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("client_id", ThirdCnst.WB.WB_APPKEY);
            map.put("client_secret", ThirdCnst.WB.WB_APP_SERCET);
            map.put("grant_type","authorization_code");
            map.put("code",code);
            map.put("redirect_uri", URLEncoder.encode(WBUtil.WB_BACKURL, "utf-8"));
            result_access_token  =  HttpConnectionUtil.instants().send(HttpConnectionUtil.METHOD_POST, WBUtil.WB_ACCESS_TOKEN_URL,map);
            System.out.println("返回包result_access_tolen===="+result_access_token);
            //将json字符串转换为java对象
            wbAccessTokenDto = (WBAccessTokenDto) JsonUtil.json2Object(result_access_token,WBAccessTokenDto.class);
        }catch (Exception e){
            System.out.println("获取access_token接口报错::::"+result_access_token);
            e.printStackTrace();
        }
        return wbAccessTokenDto;
    }

    /**
     * 获取用户信息
     * @param access_token
     * @param uid
     * @return
     */
    public static WBUserInfoDto getUserInfo(String access_token, String uid){
        WBUserInfoDto wbUserInfoDto = new WBUserInfoDto();
        Map<String,Object> map_data = null;
        String result_userInfo = null;
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("access_token",access_token);
            map.put("uid",uid);
            result_userInfo  =  HttpConnectionUtil.instants().send(HttpConnectionUtil.METHOD_GET, WBUtil.WB_GET_USER_INFO,map);
            System.out.println("返回包result_userInfo===="+result_userInfo);
            //将json字符串转换为java对象
            map_data = (Map) JsonUtil.json2Object(result_userInfo,Map.class);
            wbUserInfoDto.setId(Long.parseLong(String.valueOf(map_data.get("id"))));
            wbUserInfoDto.setIdstr(String.valueOf(map_data.get("idstr")));
            wbUserInfoDto.setScreen_name(String.valueOf(map_data.get("screen_name")));
            String gender = String.valueOf(map_data.get("gender"));
            if(gender.equals("n")){
                gender = "";
            }else if(gender.equals("m")){
                gender = "男";
            }else if(gender.equals("f")){
                gender = "女";
            }
            wbUserInfoDto.setGender(gender);
            wbUserInfoDto.setName(String.valueOf(map_data.get("name")));
            wbUserInfoDto.setProfile_image_url(String.valueOf(map_data.get("profile_image_url")));
            wbUserInfoDto.setProfile_url(String.valueOf(map_data.get("profile_url")));
        }catch (Exception e){
            System.out.println("获取result_userInfo接口报错::::"+result_userInfo);
            e.printStackTrace();
        }
        return wbUserInfoDto;
    }
}
