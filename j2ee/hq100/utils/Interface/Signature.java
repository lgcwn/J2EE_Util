package com.up72.hq.utils.Interface;

import com.up72.framework.exception.ParameterNotFoundException;
import com.up72.framework.util.ObjectUtils;
import com.up72.hq.conf.SystemConfig;
import com.up72.hq.utils.MD5Util;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * 对接sap、erp签名算法
 */
public class Signature {

    public static void main(String[] a){

    }

    /**
     * 签名算法
     * @param o 要参与签名的数据对象
     * @return 签名
     * @throws IllegalAccessException
     */
    public static String getSign(Object o,String api_secret) throws IllegalAccessException {
        String result = getStrSign(o);
        result = result+"api_secret:"+ api_secret;
        result = MD5Util.toMD5(result).toLowerCase();
        return result;
    }

    /**
     * 验证签名是否正确
     * @param signature 签名
     * @param o 头信息参数
     * @return false错误，true正确
     */
    public static boolean validateSign(String signature,Object o,String api_secret){
        boolean flag = false;
        try {
            String nowSign = getSign(o,api_secret);
            if(ObjectUtils.isNotEmpty(signature) && signature.equals(nowSign)){
                flag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    private static String getStrSign(Object o) throws IllegalAccessException{
        StringBuilder sb = new StringBuilder();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                String v = f.get(o).toString();
                String n = f.getName();
                sb.append(n + ":" + v + "&&");
            }
        }
        return sb.toString();
    }
    /**
     * 随机字符
     * @return
     */
    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    /**
     * 时间戳
     * @return
     */
    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    public static void validParamsNotNull(Object... params) throws ParameterNotFoundException {
        if (params == null || params.length < 1){
            return;
        }
        for (Object o:params) {
            if (o == null){
                throw new ParameterNotFoundException("Param cannot be null.");
            }
            if (o instanceof String){
                if(StringUtils.isBlank((String) o)){
                    throw new ParameterNotFoundException("Param cannot be null.");
                }
            }
        }
    }
}
