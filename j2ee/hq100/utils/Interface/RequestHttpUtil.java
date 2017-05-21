package com.up72.hq.utils.Interface;

import com.alibaba.fastjson.JSONObject;
import com.up72.common.util.JsonUtil;
import com.up72.hq.conf.SystemConfig;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;

public class RequestHttpUtil {

    private final static String DEFAULT_CHARSETNAME = "utf-8";

    public static <T> T postToSend(String method,String api_key,String api_secret,Object dataObj,Class<T> tClass) throws IllegalAccessException, IOException {
        RequestHeaderDto requestHeaderDto = new RequestHeaderDto();
        requestHeaderDto.setApi_key(api_key);
        requestHeaderDto.setCharset("UTF-8");
        requestHeaderDto.setFormat("JSON");
        requestHeaderDto.setMethod(method);
        requestHeaderDto.setTimestamp(Signature.create_timestamp());
        String sign = Signature.getSign(requestHeaderDto,api_secret);
        requestHeaderDto.setSignature(sign);
        String url ="http://61.148.220.94:8089/hq/games";
        String data = JSONObject.toJSONString(dataObj);
        String result = RequestHttpUtil.readHttpPost(url, data, requestHeaderDto);
        return JSONObject.parseObject(result,tClass);
    }
    public static <T> T postToSendByToken(String method,String token,String api_secret,Object dataObj,Class<T> tClass) throws IllegalAccessException, IOException {
        RequestHeaderDto requestHeaderDto = new RequestHeaderDto();
        requestHeaderDto.setCharset("UTF-8");
        requestHeaderDto.setFormat("JSON");
        requestHeaderDto.setMethod(method);
        requestHeaderDto.setToken(token);
        requestHeaderDto.setTimestamp(Signature.create_timestamp());
        String sign = Signature.getSign(requestHeaderDto,api_secret);
        requestHeaderDto.setSignature(sign);
        String url ="http://61.148.220.94:8089/hq/games";
        String data = JSONObject.toJSONString(dataObj);
        String result = RequestHttpUtil.readHttpPost(url, data, requestHeaderDto);
        return JSONObject.parseObject(result,tClass);
    }


    /**
     * post请求
     * @param url 请求地址
     * @param requestBody 请求报文
     * @param o 请求头信息内容
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static String readHttpPost(String url,String requestBody,Object o) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(url);
        StringBuilder result = new StringBuilder();
        InputStream in = null;

        try {
            initHeaderInfo(post,o);
            post.setRequestBody(requestBody);
            post.getParams().setContentCharset("UTF-8");
            // 发送http请求
            client.executeMethod(post);
            int statusCode = post.getStatusCode();
            ResponseDto responseDto = null;
            switch (statusCode){
                case 200 :
                    in = post.getResponseBodyAsStream();
                    byte[] buff = new byte[1024];
                    int flag = -1;

                    while ((flag = in.read(buff)) != -1) {
                        result.append(new String(buff, 0, flag, "UTF-8"));
                    }
                    in.close();
                    break;
                case 404 :
                    responseDto = new ResponseDto();
                    responseDto.setMessage(null);
                    responseDto.setCode("404");
                    result.append(JsonUtil.object2Json(responseDto));
                    break;
                case 500 :
                    responseDto = new ResponseDto();
                    responseDto.setMessage(null);
                    responseDto.setCode("500");
                    result.append(JsonUtil.object2Json(responseDto));
                    break;
                default :
                    responseDto = new ResponseDto();
                    responseDto.setMessage(null);
                    responseDto.setCode("-1");
                    result.append(JsonUtil.object2Json(responseDto));
                    break;
            }
        } catch (IllegalAccessException e) {
            throw new HttpException("初始化头信息异常");
        }catch (IOException e) {
            throw new HttpException("post请求异常");
        } finally {
            close(in);
            post.releaseConnection();
        }
        String returnStr = result.toString();
        System.out.println("[readHttpPost]请求URL：" + url + "\n结果：" + returnStr);
        return returnStr;
    }

    /**
     * 传输数据转码
     * @param value
     * @return
     */
    public static String twoTimesEncode(String value) {
        return twoTimesEncode(value, DEFAULT_CHARSETNAME);
    }

    private static String twoTimesEncode(String value, String charsetName) {
        return urlEncode(urlEncode(value, charsetName), charsetName);
    }

    private static String urlEncode(String value, String charsetName) {
        try {
            return java.net.URLEncoder.encode(value, charsetName);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 收到数据解码
     * @param value
     * @return
     */
    public static String twoTimesDecode(String value) {
        return twoTimesDecode(value, DEFAULT_CHARSETNAME);
    }
    private static String twoTimesDecode(String value, String charsetName) {
        return urlDecode(urlDecode(value, charsetName), charsetName);
    }
    private static String urlDecode(String value, String charsetName) {
        try {
            return java.net.URLDecoder.decode(value, charsetName);
        } catch (Exception e) {
        }
        return "";
    }

    private static void initHeaderInfo(PostMethod post,Object o) throws IllegalAccessException{
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                String v = f.get(o).toString();
                String n = f.getName();
                post.setRequestHeader(n,v);
//                System.out.println(n + "=" + v);
            }
        }
    }

    /**
     * get请求
     * @param url 请求地址
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static String readHttpGet(String url) throws HttpException, IOException {

        HttpClient client = new HttpClient();
        GetMethod get = new GetMethod(url);
        client.executeMethod(get);
        StringBuilder result = new StringBuilder();
        InputStream in = null;
        try {
            in = get.getResponseBodyAsStream();
            byte[] buff = new byte[1024];
            int flag = -1;

            while ((flag = in.read(buff)) != -1) {
                result.append(new String(buff, 0, flag, "UTF-8"));
            }
            in.close();
        } catch (IOException e) {
            throw new HttpException("get请求异常");
        } finally {
            close(in);
            get.releaseConnection();
        }
        String returnStr = result.toString();
        System.out.println("[readHttpGet]请求URL：" + url + "\n结果：" + returnStr);
        return returnStr;
    }

    private static void close(InputStream in) {
        if (null != in) {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 打印头信息
     * @param request
     */
    public static void printInfo(HttpServletRequest request){
        Enumeration enu=request.getHeaderNames();//取得全部头信息
        while(enu.hasMoreElements()){//以此取出头信息
            String headerName=(String)enu.nextElement();
            String headerValue=request.getHeader(headerName);//取出头信息内容
            System.out.println(headerName+"="+headerValue);
        }
    }

    /**
     * 获取请求中的数据
     * @throws Exception
     */
    public static String getRequestInfo(HttpServletRequest request){
        StringBuffer resp = new StringBuffer();
        try {
            InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF-8");
            char[] buff = new char[1024];
            int length = 0;
            while ((length = reader.read(buff)) != -1) {
                resp.append(new String(buff, 0, length));
            }
//            InputStream is = request.getInputStream();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            int i;
//            while ((i = is.read()) != -1) {
//                baos.write(i);
//            }
//            resp =  baos.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
        return resp.toString();
    }

    /**
     * 获取头信息内容
     * @param request
     * @return
     * @throws Exception
     */
    public static RequestHeaderDto getHeaderInfo(HttpServletRequest request) throws Exception{
        RequestHeaderDto requestHeaderDto = new RequestHeaderDto();
        Class cls = requestHeaderDto.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            String name = f.getName();
            String headerValue = request.getHeader(name);
            String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
            Method setMethod = cls.getMethod(methodName, String.class);
            setMethod.invoke(requestHeaderDto,headerValue);
        }
        return requestHeaderDto;
    }

    public static void main(String[] args) {
        try {
            ResponseDto responseDto = RequestHttpUtil.postToSend("com.dhs.tryCancelTransVouch","111111","22222","45454", ResponseDto.class);
            System.out.println(JsonUtil.object2Json(responseDto));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
