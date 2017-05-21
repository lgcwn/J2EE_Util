package com.up72.hq.utils;

import java.util.Date;

/**
 * Created by liuguicheng on 16/5/18.
 */
public class SNCode {

    public static String getTradeNo(){

        DateUtils d=new DateUtils();

        int ran=(int)(Math.random()*899999)+100000;

        return d.formatLong2Str(new Date().getTime())+""+ran;
    }

    public static void main(String arg[]){


        for(int i=0;i<100;i++){
            System.out.println(getTradeNo());
        }



    }
}
