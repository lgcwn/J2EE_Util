package com.j2ee.util;

import java.util.HashMap;

/**
 * ���ֽ��ת�������Ĵ�д���
 * 
 * @author ftpeng
 * 
 */
public class MoneyUtils {
	/**
	 * ����Ҵ�д��λ��
	 */
	private static HashMap<Integer, String> dws;
	/**
	 * ���ֶ�Ӧ������
	 */
	private static String[] jes;
	// ��ʼ��ִ��
	static {
		dws = new HashMap<Integer, String>();
		dws.put(-2, "��");
		dws.put(-1, "��");
		dws.put(0, "Ԫ");
		dws.put(1, "ʰ");
		dws.put(2, "��");
		dws.put(3, "Ǫ");
		dws.put(4, "��");//
		dws.put(5, "ʰ");
		dws.put(6, "��");
		dws.put(7, "Ǫ");
		dws.put(8, "��");//
		dws.put(9, "ʰ");
		dws.put(10, "��");
		dws.put(11, "Ǫ");
		dws.put(12, "��");
		jes = new String[] { "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��" };
	}

	/**
	 * ����ת������Ҵ�д
	 * 
	 * @param number
	 *            ���� ��֧�ֿ�ѧ����
	 * @return
	 */
	public static String chinese(String number) {
		StringBuffer su = new StringBuffer();
		// ��������
		number = delInvalidZero(number);
		String str = null;
		// С������
		String decimal = null;
		if (number.contains(".")) {
			// ��ȡ����λ
			str = number.split("\\.")[0];
			decimal = number.split("\\.")[1];
		} else {
			str = number;
		}
		// �ж��Ƿ��������λ
		if (str.length() > 0) {
			for (int i = 0; i < str.length(); i++) {
				String context = str.substring(i, i + 1);
				int pow = str.length() - i - 1;
				Integer val = Integer.parseInt(context.toString());
				// ��ȡ���ĵ�λ
				String sign = dws.get(pow);
				// ��ȡ��������
				String name = jes[Integer.parseInt(context)];
				if (val == 0) {
					if (pow % 4 != 0) {// ɾ����λ
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
		// �ж��Ƿ����С��λ
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
			su.append("��");
		}
		return su.toString();
	}

	/**
	 * �������������ַ�
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
     * �����ת�ɴ�д
     * 
     * @param value
     * @return String
     */
    public static String hangeToBig(double value)
    {
        char[] hunit = { 'ʰ', '��', 'Ǫ' }; // ����λ�ñ�ʾ
        char[] vunit = { '��', '��' }; // ������ʾ
        char[] digit = { '��', 'Ҽ', '��', '��', '��', '��', '½', '��', '��', '��' }; // ���ֱ�ʾ
        long midVal = (long) (value * 100); // ת��������
        String valStr = String.valueOf(midVal); // ת�����ַ���
        String head = valStr.substring(0, valStr.length() - 2); // ȡ��������
        String rail = valStr.substring(valStr.length() - 2); // ȡС������
        String prefix = ""; // ��������ת���Ľ��
        String suffix = ""; // С������ת���Ľ��
        // ����С����������
        if (rail.equals("00"))
        { // ���С������Ϊ0
            suffix = "��";
        }
        else
        {
            suffix = digit[rail.charAt(0) - '0'] + "��" + digit[rail.charAt(1) - '0'] + "��"; // ����ѽǷ�ת������
        }
        // ����С����ǰ�����
        char[] chDig = head.toCharArray(); // ����������ת�����ַ�����
        char zero = '0'; // ��־'0'��ʾ���ֹ�0
        byte zeroSerNum = 0; // ��������0�Ĵ���
        for (int i = 0; i < chDig.length; i++)
        { // ѭ������ÿ������
            int idx = (chDig.length - i - 1) % 4; // ȡ����λ��
            int vidx = (chDig.length - i - 1) / 4; // ȡ��λ��
            if (chDig[i] == '0')
            { // �����ǰ�ַ���0
                zeroSerNum++; // ����0��������
                if (zero == '0')
                { // ��־
                    zero = digit[0];
                }
                else if (idx == 0 && vidx > 0 && zeroSerNum < 4)
                {
                    prefix += vunit[vidx - 1];
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; // ����0��������
            if (zero != '0')
            { // �����־��Ϊ0,�����,������,��ʲô��
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; // ת�������ֱ�ʾ
            if (idx > 0)
                prefix += hunit[idx - 1];
            if (idx == 0 && vidx > 0)
            {
                prefix += vunit[vidx - 1]; // �ν���λ��Ӧ�ü��϶�������,��
            }
        }
        if (prefix.length() > 0)
            prefix += 'Բ'; // ����������ִ���,����Բ������
        return prefix + suffix; // ������ȷ��ʾ
    }

	public static void main(String[] args) {
		System.out.println(MoneyUtils.chinese("26"));
	}
}