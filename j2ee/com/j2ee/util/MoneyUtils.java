package com.j2ee.util;

import java.util.HashMap;

/**
 * 数字金额转换成中文大写金额
 * 
 * @author ftpeng
 * 
 */
public class MoneyUtils {
	/**
	 * 人民币大写单位制
	 */
	private static HashMap<Integer, String> dws;
	/**
	 * 数字对应的中文
	 */
	private static String[] jes;
	// 初始化执行
	static {
		dws = new HashMap<Integer, String>();
		dws.put(-2, "分");
		dws.put(-1, "角");
		dws.put(0, "元");
		dws.put(1, "拾");
		dws.put(2, "佰");
		dws.put(3, "仟");
		dws.put(4, "万");//
		dws.put(5, "拾");
		dws.put(6, "佰");
		dws.put(7, "仟");
		dws.put(8, "亿");//
		dws.put(9, "拾");
		dws.put(10, "佰");
		dws.put(11, "仟");
		dws.put(12, "万");
		jes = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	}

	/**
	 * 数字转换人民币大写
	 * 
	 * @param number
	 *            数字 不支持科学数字
	 * @return
	 */
	public static String chinese(String number) {
		StringBuffer su = new StringBuffer();
		// 整数部分
		number = delInvalidZero(number);
		String str = null;
		// 小数部分
		String decimal = null;
		if (number.contains(".")) {
			// 截取整数位
			str = number.split("\\.")[0];
			decimal = number.split("\\.")[1];
		} else {
			str = number;
		}
		// 判断是否存在整数位
		if (str.length() > 0) {
			for (int i = 0; i < str.length(); i++) {
				String context = str.substring(i, i + 1);
				int pow = str.length() - i - 1;
				Integer val = Integer.parseInt(context.toString());
				// 获取中文单位
				String sign = dws.get(pow);
				// 获取中文数字
				String name = jes[Integer.parseInt(context)];
				if (val == 0) {
					if (pow % 4 != 0) {// 删除单位
						sign = "";
					}
					if (i < str.length() - 1) {
						Integer val1 = Integer.parseInt(str.substring(i + 1,
								i + 2));
						if (val == 0 && val == val1) {
							name = "";
						}
					} else if (i == str.length() - 1) {
						name = "";
					}
				}
				su.append(name + sign);
			}
		}
		// 判断是否存在小数位
		if (decimal != null) {
			str = decimal.substring(0, 1);
			if (!"0".equals(str)) {
				su.append(jes[Integer.parseInt(str)] + dws.get(-1));
			}
			if (decimal.length() == 2) {
				str = decimal.substring(1, 2);
				if (!"0".equals(str)) {
					su.append(jes[Integer.parseInt(str)] + dws.get(-2));
				}
			}
		} else {
			su.append("整");
		}
		return su.toString();
	}

	/**
	 * 清理数字特殊字符
	 * 
	 * @param str
	 * @return
	 */
	private static String delInvalidZero(String str) {
		if ("0".equals(str.substring(0, 1))) {
			return delInvalidZero(str.substring(1, str.length()));
		} else if (str.contains(",")) {
			return delInvalidZero(str.replaceAll(",", ""));
		} else {
			return str;
		}
	}
	
	/**
     * 人民币转成大写
     * 
     * @param value
     * @return String
     */
    public static String hangeToBig(double value)
    {
        char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
        char[] vunit = { '万', '亿' }; // 段名表示
        char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
        long midVal = (long) (value * 100); // 转化成整形
        String valStr = String.valueOf(midVal); // 转化成字符串
        String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分
        String prefix = ""; // 整数部分转化的结果
        String suffix = ""; // 小数部分转化的结果
        // 处理小数点后面的数
        if (rail.equals("00"))
        { // 如果小数部分为0
            suffix = "整";
        }
        else
        {
            suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
        }
        // 处理小数点前面的数
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
        char zero = '0'; // 标志'0'表示出现过0
        byte zeroSerNum = 0; // 连续出现0的次数
        for (int i = 0; i < chDig.length; i++)
        { // 循环处理每个数字
            int idx = (chDig.length - i - 1) % 4; // 取段内位置
            int vidx = (chDig.length - i - 1) / 4; // 取段位置
            if (chDig[i] == '0')
            { // 如果当前字符是0
                zeroSerNum++; // 连续0次数递增
                if (zero == '0')
                { // 标志
                    zero = digit[0];
                }
                else if (idx == 0 && vidx > 0 && zeroSerNum < 4)
                {
                    prefix += vunit[vidx - 1];
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; // 连续0次数清零
            if (zero != '0')
            { // 如果标志不为0,则加上,例如万,亿什么的
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; // 转化该数字表示
            if (idx > 0)
                prefix += hunit[idx - 1];
            if (idx == 0 && vidx > 0)
            {
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
            }
        }
        if (prefix.length() > 0)
            prefix += '圆'; // 如果整数部分存在,则有圆的字样
        return prefix + suffix; // 返回正确表示
    }

	public static void main(String[] args) {
		System.out.println(MoneyUtils.chinese("26"));
	}
}