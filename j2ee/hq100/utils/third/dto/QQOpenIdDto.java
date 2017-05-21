package com.up72.hq.utils.third.dto;

import java.io.Serializable;

/**
 * 获取openId返回json
 */
public class QQOpenIdDto implements Serializable{
    /**
     * appid
     */
    private String client_id;
    /**
     * openId
     */
    private String openid;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
