package com.j2ee.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ���ڹ�����-xw�ز������� Ĭ��ʹ�� "yyyy-MM-dd HH:mm:ss" ��ʽ������
 * 
 * @author xw�ز���
 */
public final class DateUtils {
	/**
	 * Ӣ�ļ�д��Ĭ�ϣ��磺2010-12-01
	 */
	public static String FORMAT_SHORT = "yyyy-MM-dd";
	/**
	 * Ӣ��ȫ�� �磺2010-12-01 23:15:06
	 */
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	/**
	 * ��ȷ�����������ʱ�� �磺yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * ���ļ�д �磺2010��12��01��
	 */
	public static String FORMAT_SHORT_CN = "yyyy��MM��dd";
	/**
	 * ����ȫ�� �磺2010��12��01�� 23ʱ15��06��
	 */
	public static String FORMAT_LONG_CN = "yyyy��MM��dd��  HHʱmm��ss��";
	/**
	 * ��ȷ���������������ʱ��
	 */
	public static String FORMAT_FULL_CN = "yyyy��MM��dd��  HHʱmm��ss��SSS����";

	/**
	 * ���Ĭ�ϵ� date pattern
	 */
	public static String getDatePattern() {
		return FORMAT_LONG;
	}

	/**
	 * ����Ԥ���ʽ���ص�ǰ����
	 * 
	 * @return
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * �����û���ʽ���ص�ǰ����
	 * 
	 * @param format
	 * @return
	 */
	public static String getNow(String format) {
		return format(new Date(), format);
	}

	/**
	 * ʹ��Ԥ���ʽ��ʽ������
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * ʹ���û���ʽ��ʽ������
	 * 
	 * @param date
	 *            ����
	 * @param pattern
	 *            ���ڸ�ʽ
	 * @return
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * ʹ��Ԥ���ʽ��ȡ�ַ�������
	 * 
	 * @param strDate
	 *            �����ַ���
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	/**
	 * ʹ���û���ʽ��ȡ�ַ�������
	 * 
	 * @param strDate
	 *            �����ַ���
	 * @param pattern
	 *            ���ڸ�ʽ
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��������������������
	 * 
	 * @param date
	 *            ����
	 * @param n
	 *            Ҫ���ӵ�����
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * ����������������
	 * 
	 * @param date
	 *            ����
	 * @param n
	 *            Ҫ���ӵ�����
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * ��ȡʱ���
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * ��ȡ�������
	 * 
	 * @param date
	 *            ����
	 * @return
	 */
	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	/**
	 * ��Ĭ�ϸ�ʽ���ַ���������������
	 * 
	 * @param date
	 *            �����ַ���
	 * @return
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * ���û���ʽ�ַ���������������
	 * 
	 * @param date
	 *            �����ַ���
	 * @param format
	 *            ���ڸ�ʽ
	 * @return
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}
}