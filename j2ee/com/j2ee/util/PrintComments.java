package com.j2ee.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @function����ӡjava�ļ��е�����ע��
 * @author LiuGuiCheng
 * @date : 2013-4-7 ����06:57:20
 */
public class PrintComments {
    public static void main(String[] args) {
        try {
            FileReader freader = new FileReader("C://Users//administor//Workspaces//MyEclipse//javaUtil//src//com//j2ee//util//DateUtils.java");
            BufferedReader breader = new BufferedReader(freader);
            StringBuilder sb = new StringBuilder();
            try {
                String temp = "";
                /**
                 * ��ȡ�ļ����ݣ�������ȡ��ÿһ�к󶼲���\n
                 * ��Ŀ����Ϊ���ڽ���˫��б�ܣ�//��ע��ʱ��ע����ֹ��
                 */
                while((temp=breader.readLine())!= null)
                {
                    sb.append(temp);
                    sb.append('\n');
                }
                String src = sb.toString();
                /**
                 * 1����/* ע�͵�����ƥ��
                 *
                 *     ͨ����������ע�͵�����ƥ�䣬��Ϊ/*ע�����ǳɶԳ���
                 * ��ƥ�䵽һ��/*ʱ�ܻ��ڽ������������л�����ƥ�䵽"*\\/",
                 * ����ڻ�ȡ��Ӧ��"*\\/"ע��ʱֻ��Ҫ�ӵ�ǰƥ���/*��ʼ���ɣ�
                 * ��һ��ƥ��ʱֻ��Ҫ����һ��ƥ��Ľ�β��ʼ����
                 * ���������ڴ��ı����Խ�ʡƥ��Ч�ʣ�����
                 * ����ǽ���ƥ�䷨
                 *
                 * */
                Pattern leftpattern = Pattern.compile("/\\*");
                Matcher leftmatcher = leftpattern.matcher(src);
                Pattern rightpattern = Pattern.compile("\\*/");
                Matcher rightmatcher = rightpattern.matcher(src);
                sb = new StringBuilder();
                /**
                 * begin ��������������ƥ����α� {@value}
                 * ��ʼֵΪ�ļ���ͷ
                 * **/
                int begin = 0;
                while(leftmatcher.find(begin))
                {
                    rightmatcher.find(leftmatcher.start());
                    sb.append(src.substring(leftmatcher.start(), rightmatcher.end()));
                    /** Ϊ���ʱ��ʽ������ **/
                    sb.append('\n');
                    begin = rightmatcher.end();
                }
                System.out.println(sb.toString());
                /**
                 * 2����//ע�ͽ���ƥ�䣨����ƥ�䷨��
                 * ƥ�䷽���� // ������ \n �ɶԳ���
                 * */
                begin = 0;
                Pattern leftpattern1 = Pattern.compile("//");
                Matcher leftmatcher1 = leftpattern1.matcher(src);
                Pattern rightpattern1 = Pattern.compile("\n");
                Matcher rightmatcher1 = rightpattern1.matcher(src);
                sb = new StringBuilder();
                while(leftmatcher1.find(begin))
                {
                    rightmatcher1.find(leftmatcher1.start());
                    sb.append(src.substring(leftmatcher1.start(), rightmatcher1.end()));
                    begin = rightmatcher1.end();
                }
                System.out.println(sb.toString());
            } catch (IOException e) {
                System.out.println("�ļ���ȡʧ��");
            }finally
            {
                breader.close();
                freader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("�ļ�������");
        }catch(IOException e)
        {
            System.out.println("�ļ���ȡʧ��");
        }
    }

}


