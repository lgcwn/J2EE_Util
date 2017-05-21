package com.up72.hq.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 类型转换
 */
public class CastUtil {

    public static Long getLong(Map<String,Object> map, String key){
        return getObject(map,key,Long.class);
    }

    public static Integer getInteger(Map<String,Object> map, String key){
        return getObject(map,key,Integer.class);
    }

    public static Float getFloat(Map<String,Object> map, String key){
        return getObject(map,key,Float.class);
    }

    public static Double getDouble(Map<String,Object> map, String key){
        return getObject(map,key,Double.class);
    }

    public static Date getDate(Map<String,Object> map, String key){
        return getObject(map,key,Date.class);
    }

    public static BigDecimal getBigDecimal(Map<String,Object> map, String key){
        return getObject(map,key,BigDecimal.class);
    }

    public static String getString(Map<String,Object> map, String key){
        return getObject(map,key,String.class);
    }

    public static BigInteger getBigInteger(Map<String,Object> map, String key){
        return getObject(map,key,BigInteger.class);
    }

    private static <T> T getObject(Map<String,Object> map, String key, Class<T> clazz) {
        Object obj = map.get(key);
        if (obj == null) {
            return null;
        }
        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        }
        if (clazz == obj.getClass()) {
            return (T) obj;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) CastUtil.castToInt(obj);
        }

        if (clazz == long.class || clazz == Long.class) {
            return (T) CastUtil.castToLong(obj);
        }

        if (clazz == float.class || clazz == Float.class) {
            return (T) CastUtil.castToFloat(obj);
        }

        if (clazz == double.class || clazz == Double.class) {
            return (T) CastUtil.castToDouble(obj);
        }

        if (clazz == String.class) {
            return (T) CastUtil.castToString(obj);
        }

        if (clazz == BigDecimal.class) {
            return (T) CastUtil.castToBigDecimal(obj);
        }

        if (clazz == BigInteger.class) {
            return (T) CastUtil.castToBigInteger(obj);
        }

        if (clazz == Date.class) {
            return (T) CastUtil.castToDate(obj);
        }
        return null;
    }

    public static final Integer castToInt(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.length() == 0) {
                return null;
            }

            if ("null".equals(strVal)) {
                return null;
            }

            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }

            return Integer.parseInt(strVal);
        }
        return null;
    }

    public static final Long castToLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }

            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            try {
                return Long.parseLong(strVal);
            } catch (NumberFormatException ex) {
                //
            }
        }
        return null;
    }

    public static final Float castToFloat(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }

        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0) {
                return null;
            }

            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }

            return Float.parseFloat(strVal);
        }
        return null;
    }

    public static final Double castToDouble(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }

        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() == 0) {
                return null;
            }

            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }

            return Double.parseDouble(strVal);
        }
        return null;
    }
    public static String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final Date castToDate(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }

        if (value instanceof Date) {
            return (Date) value;
        }

        long longValue = -1;

        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
            return new Date(longValue);
        }

        if (value instanceof String) {
            String strVal = (String) value;

            if (strVal.indexOf('-') != -1) {
                String format;
                if (strVal.length() == DEFFAULT_DATE_FORMAT.length()) {
                    format = DEFFAULT_DATE_FORMAT;
                } else if (strVal.length() == 10) {
                    format = "yyyy-MM-dd";
                } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                    format = "yyyy-MM-dd HH:mm:ss";
                } else {
                    format = "yyyy-MM-dd HH:mm:ss.SSS";
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                try {
                    return (Date) dateFormat.parse(strVal);
                } catch (ParseException e) {
                    return null;
                }
            }

            if (strVal.length() == 0) {
                return null;
            }

            longValue = Long.parseLong(strVal);
        }

        if (longValue < 0) {
            return null;
        }

        return new Date(longValue);
    }

    public static final String castToString(Object value) {
        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public static final BigDecimal castToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }

        String strVal = value.toString();
        if (strVal.length() == 0) {
            return null;
        }

        return new BigDecimal(strVal);
    }

    public static final BigInteger castToBigInteger(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }

        if (value instanceof Float || value instanceof Double) {
            return BigInteger.valueOf(((Number) value).longValue());
        }

        String strVal = value.toString();
        if (strVal.length() == 0) {
            return null;
        }

        return new BigInteger(strVal);
    }
}
