/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2015
 */

package com.up72.hq.utils.Interface;

import java.io.Serializable;

/**
 * 参数信息
 */
public class GamesResp implements Serializable {
    /**
     * 注册应用时分配到的appId
     */
    private String appId;
    /**
     * 注册应用时分配到的appSecret
     */
    private String appSecret;
    /**
     * TOKEN
     */
    private String token;
    /**
     * 用户id
     */
    private String user_id;
    /**
     * lock
     */
    private Boolean lock;
    /**
     * virtual_coins
     */
    private Integer virtual_coins;
    /**
     * points
     */
    private Integer points;
    /**
     * name
     */
    private String name;
    /**
     * phone
     */
    private String phone;
    /**
     * phone_verify_code
     */
    private String phone_verify_code;
    /**
     * email
     */
    private String email;
    /**
     * password
     */
    private String password;
    /**
     * fullname
     */
    private String fullname;
    /**
     * return_url
     */
    private String return_url;






    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }


    public Integer getVirtual_coins() {
        return virtual_coins;
    }

    public void setVirtual_coins(Integer virtual_coins) {
        this.virtual_coins = virtual_coins;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_verify_code() {
        return phone_verify_code;
    }

    public void setPhone_verify_code(String phone_verify_code) {
        this.phone_verify_code = phone_verify_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}