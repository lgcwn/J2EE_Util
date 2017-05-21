package com.up72.hq.utils.third.dto;

import java.io.Serializable;

/**
 * 获取微信openId返回json
 */
public class WXAccessTokenDto implements Serializable{
    /**
     *接口调用凭证
     */
    private String access_token;
    /**
     *access_token接口调用凭证超时时间，单位（秒）
     */
    private int expires_in;
    /**
     *用户刷新access_token
     */
    private String refresh_token;
    /**
     *授权用户唯一标识
     */
    private String openid;
    /**
     *用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;

    /**
     *
     */
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Override
    public String toString() {
        return "WXAccessTokenDto{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", openid='" + openid + '\'' +
                ", scope='" + scope + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}
