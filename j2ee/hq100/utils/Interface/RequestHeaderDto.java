/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2015
 */

package com.up72.hq.utils.Interface;

import java.io.Serializable;

/**
 * sap/erp  请求头信息
 */
public class RequestHeaderDto  implements Serializable {
    /**
     * 注册应用时分配到的api key
     */
    private String api_key;
    /**
     * 采取命名空间的方式制定方法名
     */
    private String method;
    /**
     * 时间戳，系统时间的秒值
     */
    private String timestamp;
    /**
     * 响应包格式：JSON
     */
    private String format;
    /**
     * 编码类型：UTF-8
     */
    private String charset;
    /**
     * 参数签名，对所有参数签名
     */
    private String signature;
    /**
     * TOKEN
     */
    private String token;


    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}