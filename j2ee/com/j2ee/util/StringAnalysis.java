package com.j2ee.util;

import java.util.StringTokenizer;

public class StringAnalysis {
	/**
	 * ʵ�� �ַ�������
	 */
	
	// �ַ������������ַ���ת���ݷָ�������ַ�������
	private String[] stringAnalytical(String string, char c) {
	    int i = 0;
	    int count = 0;
	    if (string.indexOf(c) == -1)
	        return new String[] { string };// ��������ָ���򷵻��ַ�����
	    char[] cs = string.toCharArray();
	    int length = cs.length;
	    for (i = 1; i < length - 1; i++) {// ���˵���һ�������һ���Ƿָ��������
	        if (cs[i] == c) {
	            count++;// �õ��ָ����ĸ���
	        }
	    }
	    String[] strArray = new String[count + 1];
	    int k = 0, j = 0;
	    String str = string;
	    if ((k = str.indexOf(c)) == 0)// ȥ����һ���ַ��Ƿָ��������
	        str = str.substring(k + 1);
	    if (str.indexOf(c) == -1)// ����Ƿ񺬷ָ�������������򷵻��ַ���
	        return new String[] { str };
	    while ((k = str.indexOf(c)) != -1) {// �ַ������ָ����ʱ��
	        strArray[j++] = str.substring(0, k);
	        str = str.substring(k + 1);
	        if ((k = str.indexOf(c)) == -1 && str.length() > 0)
	            strArray[j++] = str.substring(0);
	    }
	    return strArray;
	}
	public void printString(String[] s) {
	    System.out.println("*********************************");
	    for (String str : s)
	        System.out.println(str);
	}
	public static void main(String[] args) {
		    String[] str = null;
		    StringAnalysis string = new StringAnalysis();
		    str = string.stringAnalytical("1aaa", '@');
		    string.printString(str);
		    str = string.stringAnalytical("2aaa@", '@');
		    string.printString(str);
		    str = string.stringAnalytical("@3aaa", '@');
		    string.printString(str);
		    str = string.stringAnalytical("4aaa@bbb", '@');
		    string.printString(str);
		    str = string.stringAnalytical("@5aaa@bbb", '@');
		    string.printString(str);
		    str = string.stringAnalytical("6aaa@bbb@", '@');
		    string.printString(str);
		    str = string.stringAnalytical("@7aaa@", '@');
		    string.printString(str);
		    str = string.stringAnalytical("@8aaa@bbb@", '@');
		    string.printString(str);
		    str = string.stringAnalytical("@9aaa@bbb@ccc", '@');
		    string.printString(str);
		    str = string.stringAnalytical("@10aaa@bbb@ccc@eee", '@');
		    string.printString(str);
		}
	}

