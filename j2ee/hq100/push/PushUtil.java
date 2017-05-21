package com.up72.hq.push;

import com.up72.hq.constant.Cnst;
import com.up72.hq.push.android.AndroidCustomizedcast;
import com.up72.hq.push.ios.IOSCustomizedcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liuguicheng on 2016/4/12 0012.
 */
public class PushUtil {
    private  static  final  Logger logger = LoggerFactory.getLogger(PushUtil.class);

    public static void sendIOSCustomizedcast(String device, String text) throws Exception {
        IOSCustomizedcast customizedcast = new IOSCustomizedcast(Cnst.Push.IOS_APPKEY, Cnst.Push.IOS_APP_MASTRER_SECRET);
        ////通知推送
        //customizedcast.setAlias("alias", Cnst.Push.IOS_ALIAS_TYPE);
        customizedcast.setAlert(text);
        customizedcast.setDeviceTokens(device);
        //上线时换成生产环境
        //customizedcast.setProductionMode();
        customizedcast.setTestMode();
        Cnst.Push.client.send(customizedcast);
        logger.error("【ios设备号:"+device+"】开始推送系统通知...........");
    }

    public static void sendIOSCustomizedcasts(String device, String text) throws Exception {
        //消息推送
        IOSCustomizedcast customizedcast = new IOSCustomizedcast(Cnst.Push.IOS_APPKEY, Cnst.Push.IOS_APP_MASTRER_SECRET);
        customizedcast.setDeviceTokens(device);
        customizedcast.setProductionMode();
        customizedcast.setContentAvailable(0);
        Cnst.Push.client.send(customizedcast);
        logger.error("【ios设备号:"+device+"】开始推送消息...........");
    }

    public static void sendAndroidCustomizedcast(String device, String text,String type) throws Exception {

        AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(Cnst.Push.ANDROID_APPKEY, Cnst.Push.ANDROID_APP_MASTRER_SECRET);
        customizedcast.setProductionMode();
        customizedcast.setDeviceTokens(device);
        if(type.equals("1")){
            customizedcast.setTicker("长彩");
            customizedcast.setTitle("长彩");
            customizedcast.setText(text);
            customizedcast.goAppAfterOpen();
            ///customizedcast.goActivityAfterOpen("com.up72.grainsinsurance.activity.SystemNoticeActivity");//用户点击通知跳转后续的 系统公告界面
            customizedcast.setDeviceTokens(device);
            /*customizedcast.setBuilderId(1);*/
            customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);//通知
            logger.error("【android设备号:"+device+"】开始推送系统通知...........");
        }else{
            customizedcast.setDescription(text);
            customizedcast.goCustomAfterOpen(text);
            customizedcast.setBuilderId(0);
            customizedcast.setDisplayType(AndroidNotification.DisplayType.MESSAGE);//消息
            logger.error("【android设备号:"+device+"】开始推送消息...........");
        }
        Cnst.Push.client.send(customizedcast);
    }

    public static void main(String[] args) {
       try {
           PushUtil.sendIOSCustomizedcast("a23ee6ae7f8e3a91f976c2f98a722b4e97358fc1c03a592a9c858fedfe13f8c0", "【最新公告】dasdasdas" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
