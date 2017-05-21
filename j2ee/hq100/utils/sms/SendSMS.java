package com.up72.hq.utils.sms;

import com.cloopen.rest.sdk.CCPRestSDK;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by liuguicheng on 16/4/28.
 */
public class SendSMS {
    public static boolean sendMsg(String mobile,String code){
        //<0>正常发送;<-2>发送参数填定不正确;<-3>用户载入延迟;<-6>密码错误:
        // <-7>用户不存在:<-11>发送号码数理大于最大发送数量:<-12>余额不足:<-99>内部处理错误:
        boolean flag=true;
        try {
            String user="cc@bjzyxy";
            String password="05hCGEEZ";
            String content= URLEncoder.encode(code + "(验证码，请勿泄露)", "GBK");
            String urlString = "http://211.147.239.62:9050/cgi-bin/sendsms?";
            StringBuffer param = new StringBuffer(urlString);
            param.append("username=");
            param.append(user);
            param.append("&password=");
            param.append(password);
            param.append("&to=");
            param.append(mobile);
            param.append("&text=");
            param.append(content);
            param.append("&msgtype=1");
            try {
                String reqParam = param.toString();
                URL url=new URL(reqParam);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setDoOutput(true);
                connection.setConnectTimeout(30 * 1000);
                connection.setRequestMethod("GET");
                connection.setUseCaches(false);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", String.valueOf(param.length()));
                connection.setDoInput(true);
                connection.connect();
                OutputStreamWriter out1 = new OutputStreamWriter(connection
                        .getOutputStream(), "UTF-8");
                out1.write(reqParam);
                out1.flush();
                out1.close();
                DataInputStream inputStream = new DataInputStream(connection.getInputStream());
                byte[] inBuf = new byte[inputStream.available()];
                int len = inBuf.length;
                int off = 0;
                int ret = 0;
                while ((ret = inputStream.read(inBuf, off, len)) > 0) {
                    off += ret;
                    len -= ret;
                }
                String result = new String(inBuf, 0, off, "UTF-8");
                inputStream.close();
                connection.disconnect();
                if(!result.trim().equals("0")){
                    flag=false;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return flag;
    }


    public static void main(String[] args) {


    }
}
