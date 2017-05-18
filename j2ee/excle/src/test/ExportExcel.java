package test;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcel<T> {
	 public void exportExcel(Collection<T> dataset, OutputStream out) {
	      exportExcel("����POI����EXCEL�ĵ�", null, dataset, out, "yyyy-MM-dd");
	   }
	 
	   public void exportExcel(String[] headers, Collection<T> dataset,
	         OutputStream out) {
	      exportExcel("����POI����EXCEL�ĵ�", headers, dataset, out, "yyyy-MM-dd");
	   }
	 
	   public void exportExcel(String[] headers, Collection<T> dataset,
	         OutputStream out, String pattern) {
	      exportExcel("����POI����EXCEL�ĵ�", headers, dataset, out, pattern);
	   }
	   
	   public void exportExcel(String title, String[] headers,
		         Collection<T> dataset, OutputStream out, String pattern) {
		      // ����һ��������
		      HSSFWorkbook workbook = new HSSFWorkbook();
		      // ����һ�����
		      HSSFSheet sheet = workbook.createSheet(title);
		      // ���ñ��Ĭ���п��Ϊ15���ֽ�
		      sheet.setDefaultColumnWidth((short) 15);
		      // ����һ����ʽ
		      HSSFCellStyle style = workbook.createCellStyle();
		      // ������Щ��ʽ
		      style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		      style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		      style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		      style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		      style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		      style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		      // ����һ������
		      HSSFFont font = workbook.createFont();
		      font.setColor(HSSFColor.VIOLET.index);
		      font.setFontHeightInPoints((short) 12);
		      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		      font.setFontName("UTF-8");
		      // ������Ӧ�õ���ǰ����ʽ
		      style.setFont(font);
		      // ���ɲ�������һ����ʽ
		      HSSFCellStyle style2 = workbook.createCellStyle();
		      style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		      style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		      style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		      style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		      style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		      style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		      style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		      style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		      // ������һ������
		      HSSFFont font2 = workbook.createFont();
		      font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		      // ������Ӧ�õ���ǰ����ʽ
		      style2.setFont(font2);
		     
		      // ����һ����ͼ�Ķ���������
		      HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		      // ����ע�͵Ĵ�С��λ��,����ĵ�
		     /* HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
		      // ����ע������
		      comment.setString(new HSSFRichTextString("������POI�����ע�ͣ�"));
		      // ����ע�����ߣ�������ƶ�����Ԫ�����ǿ�����״̬���п���������.
		      comment.setAuthor("leno");*/
		 System.out.print(headers.length);
		      //������������
		      HSSFRow row = sheet.createRow(0);
		      for (short i = 0; i < headers.length; i++) {
		         HSSFCell cell = row.createCell(i);
		         cell.setCellStyle(style);
		         HSSFRichTextString text = new HSSFRichTextString(headers[i]);
		         cell.setCellValue(text.toString());
		      }
		 
		      //�����������ݣ�����������
		      Iterator<T> it = dataset.iterator();
		      int index = 0;
		      while (it.hasNext()) {
		         index++;
		         row = sheet.createRow(index);
		         T t = (T) it.next();
		         //���÷��䣬����javabean���Ե��Ⱥ�˳�򣬶�̬����getXxx()�����õ�����ֵ
		         Field [] fields = t.getClass().getDeclaredFields();
		         for (short i = 0; i < fields.length; i++) {
		            HSSFCell cell = row.createCell(i);
		            cell.setCellStyle(style2);
		            Field field = fields[i];
		            String fieldName = field.getName();
		            String getMethodName = "get"
		                   + fieldName.substring(0, 1).toUpperCase()
		                   + fieldName.substring(1);
		            try {
		                Class tCls = t.getClass();
		                Method getMethod = tCls.getMethod(getMethodName,
		                      new Class[] {});
		                Object value = getMethod.invoke(t, new Object[] {});
		                //�ж�ֵ�����ͺ����ǿ������ת��
		                String textValue = null;

		                if (value instanceof Boolean) {
		                   boolean bValue = (Boolean) value;
		                   textValue = "��";
		                   if (!bValue) {
		                      textValue ="Ů";
		                   }
		                } else if (value instanceof Date) {
		                   Date date = (Date) value;
		                   SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		                    textValue = sdf.format(date);
		                }  else if (value instanceof byte[]) {
		                   // ��ͼƬʱ�������и�Ϊ60px;
		                   row.setHeightInPoints(60);
		                   // ����ͼƬ�����п��Ϊ80px,ע�����ﵥλ��һ������
		                   sheet.setColumnWidth(i, (short) (35.7 * 80));
		                   // sheet.autoSizeColumn(i);
		                   byte[] bsValue = (byte[]) value;
		                   HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
		                         1023, 255, (short) 6, index, (short) 6, index);
		                  // anchor.setAnchorType((int)2);
		                  /* patriarch.createPicture(anchor, workbook.addPicture(
		                         bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));*/
		                } else{
		                   //�����������Ͷ������ַ����򵥴���
		                   textValue = value.toString();
		                }
		                //�������ͼƬ���ݣ�������������ʽ�ж�textValue�Ƿ�ȫ�����������
		                if(textValue!=null){
		                   Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
		                   Matcher matcher = p.matcher(textValue);
		                   if(matcher.matches()){
		                      //�����ֵ���double����
		                      cell.setCellValue(Double.parseDouble(textValue));
		                   }else{
		                      HSSFRichTextString richString = new HSSFRichTextString(textValue);
		                      HSSFFont font3 = workbook.createFont();
		                      font3.setColor(HSSFColor.BLUE.index);
		                      richString.applyFont(font3);
		                      cell.setCellValue(richString.toString());
		                   }
		                }
		            } catch (SecurityException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } catch (NoSuchMethodException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } catch (IllegalArgumentException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } catch (IllegalAccessException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } catch (InvocationTargetException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            } finally {
		                //������Դ
		            }
		         }
		 
		      }
		      try {
		         workbook.write(out);
		      } catch (IOException e) {
		         // TODO Auto-generated catch block
		         e.printStackTrace();
		      }
		 
		   }
	 
}
