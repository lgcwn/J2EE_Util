package com.up72.hq.utils.third.dto;

import java.io.Serializable;

/**
 * 微博基础用户信息
 */
public class WBUserInfoDto implements Serializable {
    /**
     *用户UID
     */
    private long id;
    /**
     *字符串型的用户UID
     */
    private String idstr;
    /**
     *用户昵称
     */
    private String screen_name;
    /**
     *友好显示名称
     */
    private String name;
    /**
     *用户头像地址（中图），50×50像素
     */
    private String profile_image_url;
    /**
     *用户的微博统一URL地址
     */
    private String profile_url;
    /**
     *用户的微号
     */
    private String weihao;
    /**
     *性别，m：男、f：女、n：未知
     */
    private String gender;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getWeihao() {
        return weihao;
    }

    public void setWeihao(String weihao) {
        this.weihao = weihao;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "WBUserInfoDto{" +
                "id=" + id +
                ", idstr='" + idstr + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", name='" + name + '\'' +
                ", profile_image_url='" + profile_image_url + '\'' +
                ", profile_url='" + profile_url + '\'' +
                ", weihao='" + weihao + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
