package com.j2ee.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @function：打印java文件中的所有注释
 * @author LiuGuiCheng
 * @date : 2013-4-7 下午06:57:20
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
                 * 读取文件内容，并将读取的每一行后都不加\n
                 * 其目的是为了在解析双反斜杠（//）注释时做注释中止符
                 */
                while((temp=breader.readLine())!= null)
                {
                    sb.append(temp);
                    sb.append('\n');
                }
                String src = sb.toString();
                /**
                 * 1、做/* 注释的正则匹配
                 *
                 *     通过渐进法做注释的正则匹配，因为/*注释总是成对出现
                 * 当匹配到一个/*时总会在接下来的内容中会首先匹配到"*\\/",
                 * 因此在获取对应的"*\\/"注释时只需要从当前匹配的/*开始即可，
                 * 下一次匹配时只需要从上一次匹配的结尾开始即可
                 * （这样对于大文本可以节省匹配效率）——
                 * 这就是渐进匹配法
                 *
                 * */
                Pattern leftpattern = Pattern.compile("/\\*");
                Matcher leftmatcher = leftpattern.matcher(src);
                Pattern rightpattern = Pattern.compile("\\*/");
                Matcher rightmatcher = rightpattern.matcher(src);
                sb = new StringBuilder();
                /**
                 * begin 变量用来做渐进匹配的游标 {@value}
                 * 初始值为文件开头
                 * **/
                int begin = 0;
                while(leftmatcher.find(begin))
                {
                    rightmatcher.find(leftmatcher.start());
                    sb.append(src.substring(leftmatcher.start(), rightmatcher.end()));
                    /** 为输出时格式的美观 **/
                    sb.append('\n');
                    begin = rightmatcher.end();
                }
                System.out.println(sb.toString());
                /**
                 * 2、对//注释进行匹配（渐进匹配法）
                 * 匹配方法是 // 总是与 \n 成对出现
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
                System.out.println("文件读取失败");
            }finally
            {
                breader.close();
                freader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在");
        }catch(IOException e)
        {
            System.out.println("文件读取失败");
        }
    }

}


