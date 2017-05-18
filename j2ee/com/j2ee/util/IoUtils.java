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
	 * 1.�ļ�����
	 * 2.���ļ�:ʵ��ͳ��ĳһĿ¼��ÿ���ļ��г��ֵ���ĸ���������ָ������ո����������������֮��û�������ַ�
	 * 3.���ļ���d:\test.txt���в���ַ�����aa�����ֵĴ���
	 * 4.���ַ�����ȡ�ļ�
	 * 5.���ַ���д�ļ�
	 * 6.��ȡ�ļ������Ѷ�ȡ��ÿһ�д���double��������
	 */
	/**
	 * 1�� �ļ�����
	 */
	public void CopyFile(){
		try {
            File inputFile = new File(args[0]);
            if (!inputFile.exists()) {
                System.out.println("Դ�ļ������ڣ�������ֹ");
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
	 *2�� ���ļ�
	 * ʵ��ͳ��ĳһĿ¼��ÿ���ļ��г��ֵ���ĸ���������ָ������ո����������������֮��û�������ַ�
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
                    // �������ĸ
                    if (Character.isLetter(c[i1]))
                        i++;
                    // ���������
                    else if (Character.isDigit(c[i1]))
                        j++;
                    // �ǿո�
                    else if (Character.isWhitespace(c[i1]))
                        f++;
                }
                line = in.readLine();
                k++;
            }
            in.close();
            System.out
                    .println("��ĸ:" + i + ",����:" + j + ",�ո�:" + f + ",����:" + k);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	/**
	 *3�� ���ļ���d:\test.txt���в���ַ�����aa�����ֵĴ���
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
            System.out.println("\"aa\"һ��������" + count + "��");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	/**
	 * 4����ȡ�ļ������ַ���
	 */
	public void readTimes(){
		 try {
	            // ����һ
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
	            // ������
	            InputStream is = new FileInputStream(new File("d:\\1.xls"));
	            byte b[] = new byte[Integer.parseInt(new File("d:\\1.xls").length()
	                    + "")];
	            is.read(b);
	            System.out.write(b);
	            System.out.println();
	            is.close();
	            // ������
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
	 * 5��д�ļ������ַ���
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
	            // pw.print("���sssssssssssss");
	            bw.close();
	            pw.close();
	            os.close();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	/**
	 * 6����ȡ�ļ������Ѷ�ȡ��ÿһ�д���double��������
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
                sb.append(str + "��");
            }
            String str = sb.toString();
            String s[] = str.split("��");
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
