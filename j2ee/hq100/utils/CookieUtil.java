/**
 * cookie工具类
 */
package com.up72.hq.utils;

import com.up72.hq.constant.Cnst;
import com.up72.hq.constant.ShopConstans;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CookieUtil {

    public static Logger logger = Logger.getLogger(sun.reflect.Reflection
            .getCallerClass(1));

    /**
     * 写数据到cookies中
     *
     * @param response
     * @param value
     * @param seconds
     */
    public static void  setCookie(HttpServletResponse response, String key,
                                  String value, int seconds) {
        try {
            // logger.debug("key:" + key);
            // logger.debug("value:" + URLEncoder.encode(value, "UTF-8"));
            Cookie cookie = new Cookie(key, URLEncoder.encode(value, "UTF-8"));
            cookie.setMaxAge(seconds);
            // cookie.setDomain(".192.117");
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            logger.error("用户登陆写数据到cookies中出错!", e);
        }
    }

    /**
     * 通过key得到cookies中的value
     *
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key) {
        String value = null;
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
//                logger.info("cookies.length" + cookies.length);
//                logger.info("cookies[i].getName()" + cookies[i].getName());
//                logger.info("cookies[i].getValue()" + cookies[i].getValue());
                if (cookies[i].getName().equals(key)) {
                    cookie = cookies[i];
                }
            }

        }
        if (cookie != null)
            value = cookie.getValue();
        if (value == null)
            value = "";
        try {
            value = URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("通过key得到cookies中的value出错!", e);
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Cookie> toMap(Cookie[] cookies) {
        if (cookies == null || cookies.length == 0)
            return new HashMap(0);

        Map map = new HashMap(cookies.length * 2);
        for (Cookie c : cookies) {
            map.put(c.getName(), c);
        }
        return map;
    }

    /**
     *获取用户key值
     * @param request
     * @param response
     * @return
     */
    public static String cookieRandom(HttpServletRequest request, HttpServletResponse response) {
        String key = CookieUtil.getString(request, ShopConstans.Cart.KEY);
        if(StringUtils.isEmpty(key)) {
            String uuid = UUID.randomUUID().toString();
            key = uuid.toUpperCase();
            CookieUtil.setCookie(response, ShopConstans.Cart.KEY, key, ShopConstans.Cart.SECOND);
        }
        return key;
    }
    /**
     *获取用户key值
     * @param request
     * @param response
     * @return
     */
    public static String cookieIndexRandom(HttpServletRequest request, HttpServletResponse response) {
        String key = CookieUtil.getString(request, Cnst.Index.REGION);
        if(StringUtils.isEmpty(key)) {
            String uuid = UUID.randomUUID().toString();
            key = uuid.toUpperCase();
            CookieUtil.setCookie(response, Cnst.Index.REGION, key, ShopConstans.Cart.REGION_TIME);
        }
        return key;
    }
    /**
     *获取用户手机验证码 key值
     * @param request
     * @param response
     * @return
     */
    public static String cookiePhoneRandom(HttpServletRequest request, HttpServletResponse response) {
        String key = CookieUtil.getString(request, Cnst.Phone.CODE);
        if(StringUtils.isEmpty(key)) {
            String uuid = UUID.randomUUID().toString();
            key = uuid.toUpperCase();
            CookieUtil.setCookie(response, Cnst.Phone.CODE, key, ShopConstans.Cart.PHONE_CODE_TIME);
        }
        return key;
    }

    /**
     *获取用户自动登录key值
     * @param request
     * @param response
     * @return
     */
    public static String cookieAutoLoginRandom(HttpServletRequest request, HttpServletResponse response) {
        String key = CookieUtil.getString(request,Cnst.AutoLogin.AUTO_LOGIN_KEY);
        if(StringUtils.isEmpty(key)) {
            String uuid = UUID.randomUUID().toString();
            key = uuid.toUpperCase();
            CookieUtil.setCookie(response, Cnst.AutoLogin.AUTO_LOGIN_KEY, key,Cnst.AutoLogin.AUTO_LOGIN_SECOND);
        }
        return key;
    }


    /**
     * 获取用户单点登录key值
     * @param request
     * @param response
     * @return
     */
    public static String cookieSingleLoginRandom(HttpServletRequest request, HttpServletResponse response) {
        String key = CookieUtil.getString(request,Cnst.SingleLogin.SINGLE_LOGIN_KEY);
        if(StringUtils.isEmpty(key)) {
            String uuid = UUID.randomUUID().toString();
            key = uuid.toUpperCase();
            CookieUtil.setCookie(response,Cnst.SingleLogin.SINGLE_LOGIN_KEY, key,Cnst.SingleLogin.SINGLE_LOGIN_SECOND);
        }
        return key;
    }






}
