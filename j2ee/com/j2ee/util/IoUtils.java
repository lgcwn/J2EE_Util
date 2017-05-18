package com.j2ee.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IoUtils {
	public static String [] args;
	/**
	 * 1.文件拷贝
	 * 2.读文件:实现统计某一目录下每个文件中出现的字母个数、数字个数、空格个数及行数，除此之外没有其他字符
	 * 3.从文件（d:\test.txt）中查出字符串”aa”出现的次数
	 * 4.三种方法读取文件
	 * 5.三种方法写文件
	 * 6.读取文件，并把读取的每一行存入double型数组中
	 */
	/**
	 * 1、 文件拷贝
	 */
	public void CopyFile(){
		try {
            File inputFile = new File(args[0]);
            if (!inputFile.exists()) {
                System.out.println("源文件不存在，程序终止");
                System.exit(1);
            }
            File outputFile = new File(args[1]);
            InputStream in = new FileInputStream(inputFile);
            OutputStream out = new FileOutputStream(outputFile);
            byte date[] = new byte[1024];
            int temp = 0;
            while ((temp = in.read(date)) != -1) {
                out.write(date);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	/**
	 *2、 读文件
	 * 实现统计某一目录下每个文件中出现的字母个数、数字个数、空格个数及行数，除此之外没有其他字符
	 */
	public void readFile(){
		String fileName = "D:/date.java.bak";
        // String fileName = "D:/test.qqq";
        String line;
        int i = 0, j = 0, f = 0, k = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            line = in.readLine();
            while (line != null) {
                // System.out.println(line);
                char c[] = line.toCharArray();
                for (int i1 = 0; i1 < c.length; i1++) {
                    // 如果是字母
                    if (Character.isLetter(c[i1]))
                        i++;
                    // 如果是数字
                    else if (Character.isDigit(c[i1]))
                        j++;
                    // 是空格
                    else if (Character.isWhitespace(c[i1]))
                        f++;
                }
                line = in.readLine();
                k++;
            }
            in.close();
            System.out
                    .println("字母:" + i + ",数字:" + j + ",空格:" + f + ",行数:" + k);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	/**
	 *3、 从文件（d:\test.txt）中查出字符串”aa”出现的次数
	 */
	public void readCharTime(){
		try {
            BufferedReader br = new BufferedReader(new FileReader(
                    "D:\\test.txt"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String str = br.readLine();
                if (str == null)
                    break;
                sb.append(str);
            }
            Pattern p = Pattern.compile("aa");
            Matcher m = p.matcher(sb);
            int count = 0;
            while (m.find()) {
                count++;
            }
            System.out.println("\"aa\"一共出现了" + count + "次");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	/**
	 * 4、读取文件的三种方法
	 */
	public void readTimes(){
		 try {
	            // 方法一
	            BufferedReader br = new BufferedReader(new FileReader(new File(
	                    "D:\\1.xls")));
	            // StringBuilder bd = new StringBuilder();
	            StringBuffer bd = new StringBuffer();
	            while (true) {
	                String str = br.readLine();
	                if (str == null) {
	                    break;
	                }
	                System.out.println(str);
	                bd.append(str);
	            }
	            br.close();
	            // System.out.println(bd.toString());
	            // 方法二
	            InputStream is = new FileInputStream(new File("d:\\1.xls"));
	            byte b[] = new byte[Integer.parseInt(new File("d:\\1.xls").length()
	                    + "")];
	            is.read(b);
	            System.out.write(b);
	            System.out.println();
	            is.close();
	            // 方法三
	            Reader r = new FileReader(new File("d:\\1.xls"));
	            char c[] = new char[(int) new File("d:\\1.xls").length()];
	            r.read(c);
	            String str = new String(c);
	            System.out.print(str);
	            r.close();
	        } catch (RuntimeException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	/**
	 * 5、写文件的三种方法
	 */
	public void writeFiles(){
		 try {
	            PrintWriter pw = new PrintWriter(new FileWriter("d:\\1.txt"));
	            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
	                    "d:\\1.txt")));
	            OutputStream os = new FileOutputStream(new File("d:\\1.txt"));
	            // 1
	            os.write("ffff".getBytes());
	            // 2
	            // bw.write("ddddddddddddddddddddddddd");
	            // 3
	            // pw.print("你好sssssssssssss");
	            bw.close();
	            pw.close();
	            os.close();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	/**
	 * 6、读取文件，并把读取的每一行存入double型数组中
	 */
	public void read(){
		try {
            BufferedReader br = new BufferedReader(new FileReader(new File(
                    "d:\\2.txt")));
            StringBuffer sb = new StringBuffer();
            while (true) {
                String str = br.readLine();
                if (str == null) {
                    break;
                }
                sb.append(str + "、");
            }
            String str = sb.toString();
            String s[] = str.split("、");
            double d[] = new double[s.length];
            for (int i = 0; i < s.length; i++) {
                d[i] = Double.parseDouble(s[i]);
            }
            for (int i = 0; i < d.length; i++) {
                System.out.println(d[i]);
            }
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
} 
