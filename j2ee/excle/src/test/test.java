package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // ����ѧ��
	      ExportExcel<Student> ex = new ExportExcel<Student>();
	      String[] headers = { "ѧ��", "����", "����", "�Ա�", "��������" };
	      List<Student> dataset = new ArrayList<Student>();
	      dataset.add(new Student(10000001, "����", 20, true, new Date()));
	      dataset.add(new Student(20000002, "����", 24, false, new Date()));
	      dataset.add(new Student(30000003, "����", 22, true, new Date()));
	      // ����ͼ��
	      ExportExcel<Book> ex2 = new ExportExcel<Book>();
	      String[] headers2 = { "ͼ����", "ͼ������", "ͼ������", "ͼ��۸�", "ͼ��ISBN",
	            "ͼ�������", "����ͼƬ" };
	      List<Book> dataset2 = new ArrayList<Book>();
	      try {
	         BufferedInputStream bis = new BufferedInputStream(
	                new FileInputStream("F://ͼƬ//2e.jpg"));
	         byte[] buf = new byte[bis.available()];
	         while ((bis.read(buf)) != -1) {
	            //
	         }
	         dataset2.add(new Book(1, "jsp", "leno", 300.33f, "1234567",
	                "�廪������", buf));
	         dataset2.add(new Book(2, "java���˼��", "brucl", 300.33f, "1234567",
	                "���������", buf));
	         dataset2.add(new Book(3, "DOM����", "lenotang", 300.33f, "1234567",
	                "�廪������", buf));
	         dataset2.add(new Book(4, "c++����", "leno", 400.33f, "1234567",
	                "�廪������", buf));
	         dataset2.add(new Book(5, "c#����", "leno", 300.33f, "1234567",
	                "�����������", buf));
	 
	         OutputStream out = new FileOutputStream("E://a.xls");
	         OutputStream out2 = new FileOutputStream("E://b.xls");
	         ex.exportExcel(headers, dataset, out);
	         ex2.exportExcel(headers2, dataset2, out2);
	         out.close();
	         out2.close();
	         JOptionPane.showMessageDialog(null, "�����ɹ�!");
	      } catch (FileNotFoundException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	   }
	}
