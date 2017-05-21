/*
 * Powered By [up72-framework]
 * Web Site: http://www.up72.com
 * Since 2006 - 2015
 */

package com.up72.hq.utils.Interface;

/**
 * sap/erp  响应信息
 */
public class ResponseDto {
    /**
     * 响应码
     */
    private String code;
    /**
     * 响应信息
     */
    private String message;

    /**
     * 数据
     * @return
     */
    private Object data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data==null?"":data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}