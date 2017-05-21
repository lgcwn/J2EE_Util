package com.up72.hq.utils.third.dto;


/**
 * 接收微博的access_token的返回信息
 */
public class WBAccessTokenDto {
    /**
     * access_token
     */
    private String access_token;
    /**
     * remind_in  提醒周期
     */
    private String remind_in;
    /**
     * expires_in  生命周期
     */
    private String expires_in;
    /**
     * uid 用户id
     */
    private String uid;
    /**
     *
     */
    private String scope;


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRemind_in() {
        return remind_in;
    }

    public void setRemind_in(String remind_in) {
        this.remind_in = remind_in;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "WBAccessTokenDto{" +
                "access_token='" + access_token + '\'' +
                ", remind_in='" + remind_in + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", uid='" + uid + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
