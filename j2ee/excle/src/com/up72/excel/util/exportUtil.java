package com.up72.excel.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
 
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
/**
 * @corpor: ��˾����Ѷ�ſ�
 * @author: ���ߣ�̷����
 * @explain: ���壺���ݵ�����Excel
 * @version: ���ڣ�2012-9-14 ����05:50:06
 */
public class exportUtil {
		
		public static   HSSFWorkbook wb;
	    private static CellStyle titleStyle;        // ��������ʽ
	    private static Font titleFont;              // ����������        
	    private static CellStyle dateStyle;         // ��������ʽ
	    private static Font dateFont;               // ����������
	    private static CellStyle headStyle;         // ��ͷ����ʽ
	    private static Font headFont;               // ��ͷ������
	    private static CellStyle contentStyle ;     // ��������ʽ
	    private static Font contentFont;            // ����������
	     
	    /**
	     * @throws IllegalAccessException 
	     * @throws IllegalArgumentException 
	     * @Description: ��Map��ļ��϶����������Excel������
	     */
	    @SuppressWarnings({ "unchecked" })
	    public static void export2Excel(ExportSetInfo setInfo) throws
	        IOException, IllegalArgumentException, IllegalAccessException
	    {
	        init();
	        Set<Entry<String, List>> set = setInfo.getObjsMap().entrySet();
	        String[] sheetNames = new String[setInfo.getObjsMap().size()];
	        int sheetNameNum = 0;
	        for (Entry<String, List> entry : set)
	        {
	            sheetNames[sheetNameNum] = entry.getKey();
	            sheetNameNum++;
	        }
	        HSSFSheet[] sheets = getSheets(setInfo.getObjsMap().size(), sheetNames);
	        int sheetNum = 0;
	        for (Entry<String, List> entry : set)
	        {
	            // Sheet
	            List objs = entry.getValue();
	            // ������
	            createTableTitleRow(setInfo, sheets, sheetNum);
	            // ������
	            createTableDateRow(setInfo, sheets, sheetNum);
	            // ��ͷ
	            creatTableHeadRow(setInfo, sheets, sheetNum);
	            // ����
	            String[] fieldNames = setInfo.getFieldNames().get(sheetNum);
	            int rowNum = 3;
	            for (Object obj : objs)
	            {
	                HSSFRow contentRow = sheets[sheetNum].createRow(rowNum);
	                contentRow.setHeight((short) 300);
	                HSSFCell[] cells = getCells(contentRow, setInfo.getFieldNames().get(sheetNum).length);
	                int cellNum = 1;                    // ȥ��һ����ţ���˴�1��ʼ
	                if(fieldNames != null)
	                {
	                    for (int num = 0; num < fieldNames.length; num++)
	                    {
	                        Object value = ReflectionUtils.invokeGetterMethod(obj, fieldNames[num]);
	                        cells[cellNum].setCellValue(value == null ? "" : value.toString());
	                        cellNum++;
	                    }
	                }
	                rowNum++;
	            }
//	          adjustColumnSize(sheets, sheetNum, fieldNames); // �Զ������п�
	            sheetNum++;
	        }
	        wb.write(setInfo.getOut());
	    }
	 
	    /**
	     * @Description: ��ʼ��
	     */
	    private static void init()
	    {
	        wb = new HSSFWorkbook();
	         
	        titleFont = wb.createFont();
	        titleStyle = wb.createCellStyle();
	        dateStyle = wb.createCellStyle();
	        dateFont = wb.createFont();
	        headStyle = wb.createCellStyle();
	        headFont = wb.createFont();
	        contentStyle = wb.createCellStyle();
	        contentFont = wb.createFont();
	         
	        initTitleCellStyle();
	        initTitleFont();
	        initDateCellStyle();
	        initDateFont();
	        initHeadCellStyle();
	        initHeadFont();
	        initContentCellStyle();
	        initContentFont();
	    }
	 
	    /**
	     * @Description: �Զ������п�
	     */
	    @SuppressWarnings("unused")
	    private static void adjustColumnSize(HSSFSheet[] sheets, int sheetNum,
	            String[] fieldNames)
	    {
	        for(int i = 0; i < fieldNames.length + 1; i++)
	        {
	            sheets[sheetNum].autoSizeColumn(i, true);
	        }
	    }
	 
	    /**
	     * @Description: ����������(��ϲ���Ԫ��)
	     */
	    private static void createTableTitleRow(ExportSetInfo setInfo,
	            HSSFSheet[] sheets, int sheetNum)
	    {
	        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, 
	                setInfo.getFieldNames().get(sheetNum).length);
	        sheets[sheetNum].addMergedRegion(titleRange);
	        HSSFRow titleRow = sheets[sheetNum].createRow(0);
	        titleRow.setHeight((short) 800);
	        HSSFCell titleCell = titleRow.createCell(0);
	        titleCell.setCellStyle(titleStyle);
	        titleCell.setCellValue(setInfo.getTitles()[sheetNum]);
	    }
	 
	    /**
	     * @Description: ����������(��ϲ���Ԫ��)
	     */
	    private static void createTableDateRow(ExportSetInfo setInfo,
	            HSSFSheet[] sheets, int sheetNum)
	    {
	        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, 
	                setInfo.getFieldNames().get(sheetNum).length);
	        sheets[sheetNum].addMergedRegion(dateRange);
	        HSSFRow dateRow = sheets[sheetNum].createRow(1);
	        dateRow.setHeight((short) 350);
	        HSSFCell dateCell = dateRow.createCell(0);
	        dateCell.setCellStyle(dateStyle);
	        dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	    }
	 
	    /**
	     * @Description: ������ͷ��(��ϲ���Ԫ��)
	     */
	    private static void creatTableHeadRow(ExportSetInfo setInfo,
	            HSSFSheet[] sheets, int sheetNum)
	    {
	        // ��ͷ
	        HSSFRow headRow = sheets[sheetNum].createRow(2);
	        headRow.setHeight((short) 350);
	        // �����
	        HSSFCell snCell = headRow.createCell(0);
	        snCell.setCellStyle(headStyle);
	        snCell.setCellValue("���");
	        // ��ͷ����
	        for(int num = 1, len = setInfo.getHeadNames().get(sheetNum).length; num <= len; num++)
	        {
	            HSSFCell headCell = headRow.createCell(num);
	            headCell.setCellStyle(headStyle);
	            headCell.setCellValue(setInfo.getHeadNames().get(sheetNum)[num - 1]);
	        }
	    }
	 
	    /**
	     * @Description: �������е�Sheet
	     */
	    private static HSSFSheet[] getSheets(int num, String[] names)
	    {
	        HSSFSheet[] sheets = new HSSFSheet[num];
	        for (int i = 0; i < num; i++)
	        {
	            sheets[i] = wb.createSheet(names[i]);
	        }
	        return sheets;
	    }
	 
	    /**
	     * @Description: ���������е�ÿһ��(����һ�����)
	     */
	    private static HSSFCell[] getCells(HSSFRow contentRow, int num)
	    {
	        HSSFCell[] cells = new HSSFCell[num + 1];
	 
	        for (int i = 0,len = cells.length; i < len; i++)
	        {
	            cells[i] = contentRow.createCell((short)i);
	            cells[i].setCellStyle(contentStyle);
	        }
	        // ���������ֵ����Ϊ��ȥ�����к������У�����-2
	        cells[0].setCellValue(contentRow.getRowNum() - 2);
	 
	        return cells;
	    }
	 
	    /**
	     * @Description: ��ʼ����������ʽ
	     */
	    private static void initTitleCellStyle()
	    {
	        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        titleStyle.setFont(titleFont);
	        titleStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
	    }
	 
	    /**
	     * @Description: ��ʼ����������ʽ
	     */
	    private static void initDateCellStyle()
	    {
	        dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
	        dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        dateStyle.setFont(dateFont);
	        dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
	    }
	 
	    /**
	     * @Description: ��ʼ����ͷ����ʽ
	     */
	    private static void initHeadCellStyle()
	    {
	        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        headStyle.setFont(headFont);
	        headStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
	        headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
	        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
	        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
	        headStyle.setBorderRight(CellStyle.BORDER_THIN);
	        headStyle.setTopBorderColor(IndexedColors.BLUE.index);
	        headStyle.setBottomBorderColor(IndexedColors.BLUE.index);
	        headStyle.setLeftBorderColor(IndexedColors.BLUE.index);
	        headStyle.setRightBorderColor(IndexedColors.BLUE.index);
	    }
	 
	    /**
	     * @Description: ��ʼ����������ʽ
	     */
	    private static void initContentCellStyle()
	    {
	        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        contentStyle.setFont(contentFont);
	        contentStyle.setBorderTop(CellStyle.BORDER_THIN);
	        contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
	        contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
	        contentStyle.setBorderRight(CellStyle.BORDER_THIN);
	        contentStyle.setTopBorderColor(IndexedColors.BLUE.index);
	        contentStyle.setBottomBorderColor(IndexedColors.BLUE.index);
	        contentStyle.setLeftBorderColor(IndexedColors.BLUE.index);
	        contentStyle.setRightBorderColor(IndexedColors.BLUE.index);
	        contentStyle.setWrapText(true); // �ֶλ���
	    }
	     
	    /**
	     * @Description: ��ʼ������������
	     */
	    private static void initTitleFont()
	    {
	        titleFont.setFontName("���Ŀ���");
	        titleFont.setFontHeightInPoints((short) 20);
	        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        titleFont.setCharSet(Font.DEFAULT_CHARSET);
	        titleFont.setColor(IndexedColors.BLUE_GREY.index);
	    }
	 
	    /**
	     * @Description: ��ʼ������������
	     */
	    private static void initDateFont()
	    {
	        dateFont.setFontName("����");
	        dateFont.setFontHeightInPoints((short) 10);
	        dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        dateFont.setCharSet(Font.DEFAULT_CHARSET);
	        dateFont.setColor(IndexedColors.BLUE_GREY.index);
	    }
	 
	    /**
	     * @Description: ��ʼ����ͷ������
	     */
	    private static void initHeadFont()
	    {
	        headFont.setFontName("����");
	        headFont.setFontHeightInPoints((short) 10);
	        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        headFont.setCharSet(Font.DEFAULT_CHARSET);
	        headFont.setColor(IndexedColors.BLUE_GREY.index);
	    }
	 
	    /**
	     * @Description: ��ʼ������������
	     */
	    private static void initContentFont()
	    {
	        contentFont.setFontName("����");
	        contentFont.setFontHeightInPoints((short) 10);
	        contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
	        contentFont.setCharSet(Font.DEFAULT_CHARSET);
	        contentFont.setColor(IndexedColors.BLUE_GREY.index);
	    }
	     
	     
	    /**
	     * @Description: ��װExcel������������Ϣ
	     * @author: ̷����
	     */
	    public static class ExportSetInfo
	    {
	        @SuppressWarnings("unchecked")
	        private LinkedHashMap<String, List> objsMap;
	         
	        private String[] titles;
	         
	        private List<String[]> headNames;
	         
	        private List<String[]> fieldNames;
	         
	        private OutputStream out;
	 
	         
	        @SuppressWarnings("unchecked")
	        public LinkedHashMap<String, List> getObjsMap()
	        {
	            return objsMap;
	        }
	 
	        /**
	         * @param objMap ��������
	         * 
	         * ����
	         * String : ����sheet����
	         * List : ������sheet�������������
	         */
	        @SuppressWarnings("unchecked")
	        public void setObjsMap(LinkedHashMap<String, List> objsMap)
	        {
	            this.objsMap = objsMap;
	        }
	 
	        public List<String[]> getFieldNames()
	        {
	            return fieldNames;
	        }
	 
	        /**
	         * @param clazz ��Ӧÿ��sheet���ÿ�����ݵĶ������������
	         */
	        public void setFieldNames(List<String[]> fieldNames)
	        {
	            this.fieldNames = fieldNames;
	        }
	 
	        public String[] getTitles()
	        {
	            return titles;
	        }
	 
	        /**
	         * @param titles ��Ӧÿ��sheet��ı��⣬����������
	         */
	        public void setTitles(String[] titles)
	        {
	            this.titles = titles;
	        }
	 
	        public List<String[]> getHeadNames()
	        {
	            return headNames;
	        }
	 
	        /**
	         * @param headNames ��Ӧÿ��ҳǩ�ı�ͷ��ÿһ�е�����
	         */
	        public void setHeadNames(List<String[]> headNames)
	        {
	            this.headNames = headNames;
	        }
	 
	        public OutputStream getOut()
	        {
	            return out;
	        }
	 
	        /**
	         * @param out Excel���ݽ�������������
	         */
	        public void setOut(OutputStream out)
	        {
	            this.out = out;
	        }
	    }
	}

