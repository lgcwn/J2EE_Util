package com.up72.hq.utils;

import com.up72.common.util.JsonUtil;
import com.up72.hq.conf.SystemConfig;
import com.up72.hq.constant.ThirdCnst;
import com.up72.hq.utils.http.HttpConnectionUtil;
import com.up72.hq.utils.third.dto.QQOpenIdDto;
import com.up72.hq.utils.third.dto.QQUserInfoDto;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * qq登录工具类
 */
public class QQUtil {
    //安全校验
    public final static String state = "dahuaishu";
    //回调地址


    public final static String QQ_BACKURL = SystemConfig.instants().getValue("QQ_BACKURL");

    //获取token
    public static String QQ_ACCESS_TOKEN_URL = SystemConfig.instants().getValue("QQ_ACCESS_TOKEN_URL");
    //获取openId
    public static String QQ_OPEN_ID_URL = SystemConfig.instants().getValue("QQ_OPEN_ID_URL");
    //获取用户信息
    public static String QQ_GET_USER_INFO_URL = SystemConfig.instants().getValue("QQ_GET_USER_INFO_URL");

    /**
     * 获取qq用户的access_token
     * @param code
     * @return
     */
    public static String getACCESS_TOKEN(String code){
        String access_token = "";
        String result_access_token = null;
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("grant_type","authorization_code");
            map.put("code",code);
            map.put("client_id", ThirdCnst.QQ.QQ_APPID);
            map.put("client_secret", ThirdCnst.QQ.QQ_APPKEY);
            map.put("redirect_uri", URLEncoder.encode(QQUtil.QQ_BACKURL, "utf-8"));
            result_access_token  =  HttpConnectionUtil.instants().send(HttpConnectionUtil.METHOD_GET,QQUtil.QQ_ACCESS_TOKEN_URL,map);
            System.out.println("返回包result_access_tolen===="+result_access_token);
            //截取字符串获取
            access_token =  result_access_token.split("&")[0].split("=")[1];
            System.out.println("access_token===="+access_token);
        }catch (Exception e){
            System.out.println("获取access_token接口报错::::"+result_access_token);
            e.printStackTrace();
        }
        return access_token;
    }

    /**
     * 获取qq用户的openId
     * @param access_token
     * @return
     */
    public static String getOPEN_ID(String access_token){
        String openId = "";
        String result_openId = null;
        String openJson = null;
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("access_token",access_token);
            result_openId  =  HttpConnectionUtil.instants().send(HttpConnectionUtil.METHOD_GET,QQUtil.QQ_OPEN_ID_URL,map);
            //返回值格式：：：callback( {"client_id":"101269471","openid":"F248D09DFD83D11E90EC8E545B115D27"} );
            System.out.println("返回包result_openId===="+result_openId);
            //截取openId
            Pattern p = Pattern.compile(" (.*) ");
            Matcher m = p.matcher(result_openId);
            while(m.find())
                openJson = m.group(1);
            QQOpenIdDto qqOpenIdDto = (QQOpenIdDto) JsonUtil.json2Object(openJson, QQOpenIdDto.class);
            openId = qqOpenIdDto.getOpenid();
            System.out.println("openId===="+openId);
        }catch (Exception e){
            System.out.println("获取openId接口报错::::"+result_openId);
            e.printStackTrace();
        }
        return openId;
    }

    /**
     * 获取qq用户的信息
     * @param openId
     * @param access_token
     * @return
     */
    public static QQUserInfoDto getUserInfoDto(String openId,String access_token){
        String result_userInfo = null;
        QQUserInfoDto qqUserInfoDto = null;
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("access_token",access_token);
            map.put("openid",openId);
            map.put("oauth_consumer_key", ThirdCnst.QQ.QQ_APPID);
            result_userInfo  =  HttpConnectionUtil.instants().send(HttpConnectionUtil.METHOD_GET,QQUtil.QQ_GET_USER_INFO_URL,map);
            System.out.println("返回包result_userInfo===="+result_userInfo);
            qqUserInfoDto = (QQUserInfoDto) JsonUtil.json2Object(result_userInfo, QQUserInfoDto.class);
        }catch (Exception e){
            System.out.println("获取用户信息接口报错::::"+result_userInfo);
            e.printStackTrace();
        }
        return qqUserInfoDto;
    }
}
