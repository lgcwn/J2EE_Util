package com.j2ee.util;

import java.util.StringTokenizer;

public class StringAnalysis {
	/**
	 * 实现 字符串解析
	 */
	
	// 字符串解析，将字符串转根据分割符换成字符串数组
	private String[] stringAnalytical(String string, char c) {
	    int i = 0;
	    int count = 0;
	    if (string.indexOf(c) == -1)
	        return new String[] { string };// 如果不含分割符则返回字符本身
	    char[] cs = string.toCharArray();
	    int length = cs.length;
	    for (i = 1; i < length - 1; i++) {// 过滤掉第一个和最后一个是分隔符的情况
	        if (cs[i] == c) {
	            count++;// 得到分隔符的个数
	        }
	    }
	    String[] strArray = new String[count + 1];
	    int k = 0, j = 0;
	    String str = string;
	    if ((k = str.indexOf(c)) == 0)// 去掉第一个字符是分隔符的情况
	        str = str.substring(k + 1);
	    if (str.indexOf(c) == -1)// 检测是否含分隔符，如果不含则返回字符串
	        return new String[] { str };
	    while ((k = str.indexOf(c)) != -1) {// 字符串含分割符的时候
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

