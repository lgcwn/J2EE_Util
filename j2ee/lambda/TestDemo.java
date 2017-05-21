package lambda;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;

/**
 * Created by LiuGuicheng on 2016/4/4.
 */
public class TestDemo {
    @Test
    public  void test01(){
        String a="aaaa";
        String b="vvvvvvvvvv";
        Comparator.comparing(String::length);
    }
    @Test
    public  void test02(){
        File file = new File("D:\\1.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            StringBuffer  stringBuffer=new StringBuffer();
            int line = 1;
            //һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null){
                tempString=tempString.trim();
                //��ʾ�к�
                String sex="";
                String name=tempString.substring(0, 3).trim();
                /*if(name.length()==2){
                    sex=tempString.substring(2, 10);
                }else{*/
                    sex=tempString.substring(3, 5).trim();
               /* }*/
                String account=tempString.substring(6, 17).trim();
                String vv=tempString.substring(tempString.length()-8, tempString.length()).trim();
               /* System.out.println("ACCOUNT:" + account);
                System.out.println("NAME:" + name);
                System.out.println("sex:" + sex);
                System.out.println("vv:" + vv);
                System.out.println("PASSWORD:" + TestDemo.md5(account));*/
                line++;
                stringBuffer.append("INSERT INTO park_member(ACCOUNT,PASSWORD,NAME,MOBILE,HEAD_IMAGE,GENDER,CREATE_TIME,IS_DEL)   " +
                        " VALUES('"+account+"','"+TestDemo.md5(account)+"','"+name+"',"+account+",'/styles/images/head_portrait.png','"+sex+"','2016-05-20 23:48:48',1);\n");
            }
            reader.close();
            System.out.println(stringBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    public static String md5(String source) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(source.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(
                        Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }

        return md5StrBuff.toString();
    }
}
