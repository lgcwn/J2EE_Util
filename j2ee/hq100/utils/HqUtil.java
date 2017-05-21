package com.up72.hq.utils;

import com.up72.framework.util.holder.ApplicationContextHolder;
import com.up72.hq.constant.Cnst;
import com.up72.hq.dao.MemberMapper;
import com.up72.hq.dao.RuleManagerOrderMapper;
import com.up72.hq.dto.resp.MemberResp;
import com.up72.hq.model.Member;
import com.up72.hq.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by from on 2016/6/24.
 */
public class HqUtil {
    /**获取当前登录的会员*/
    public  static MemberResp getLoginMember(HttpServletRequest request){
        MemberResp memberResp = (MemberResp) request.getSession().getAttribute(Cnst.Member.LOGIN_MEMBER);
        return  memberResp;
    }
    public  static void setSessionByMember(HttpServletRequest request,MemberResp memberResp){
        request.getSession().setAttribute(Cnst.Member.LOGIN_MEMBER,memberResp);
    }
    public  static Long getLoginMemberId(HttpServletRequest request){
        return getLoginMember(request).getId();
    }
    public static  String getIpRemoteHost(javax.servlet.http.HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

    public static Map<Integer,Object> getYear(){
        Map<Integer,Object> yearMap=new LinkedHashMap<Integer,Object>();
        Calendar a=Calendar.getInstance();
        int year=a.get(Calendar.YEAR);//得到当前年份
        for (int i = 0; i <10 ; i++) {
            yearMap.put(year,year);
            year=--year;
        }
        return  yearMap;
    }
    public static void deleteFile(String sPath) {
        File file = new File(sPath);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }


}
