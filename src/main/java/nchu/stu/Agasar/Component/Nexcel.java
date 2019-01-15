package nchu.stu.Agasar.Component;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class Nexcel {
    public static List<String> createTittle(String path) throws Exception {
        XSSFRow row = null;
        List<String> list=new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File(path));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        fis.close();
        //可以增加的内容
        for(int i=0;i<workbook.getNumberOfSheets();i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            Iterator<Row> rowIterator = sheet.iterator();
            if (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next();
                Iterator<Cell> cellIterable = row.cellIterator();
                while ((cellIterable.hasNext())) {
                    String str = null;
                    Cell cell = cellIterable.next();
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            str=cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            str=String.valueOf(cell.getNumericCellValue());
                            break;
                    }
                    list.add(str);
                }
            }
        }
        for(String str:list)
            System.out.println(str);
        return list;
    }
    public static void add(final int sheetNum,final int targetNum,List<String> list,String path) throws Exception {
        XSSFRow row = null;
        FileInputStream fis = new FileInputStream(new File(path));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        fis.close();
        //解析表格的标题
        XSSFSheet sheet = workbook.getSheetAt(sheetNum-1);
        Iterator<Row> rowIterator = sheet.iterator();
        int rowNum = 0;
        int temp = 0;
        while (rowIterator.hasNext()) {
            rowNum++;
            rowIterator.next();
        }
        rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            temp++;
            row = (XSSFRow) rowIterator.next();
            Iterator<Cell> cellIterable = row.cellIterator();
            if (temp == targetNum) {
                int clos=0;//列数
                XSSFRow newRow=sheet.createRow(rowNum);
                while ((cellIterable.hasNext())) {
                    Cell cell = cellIterable.next();
                    XSSFCell newCell=newRow.createCell(clos);
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            CellStyle styleS=cell.getCellStyle();
                            newCell.setCellValue(list.get(clos));
                            newCell.setCellStyle(styleS);
                            break;
                        case NUMERIC:
                            CellStyle styleN=cell.getCellStyle();
                            if(styleN.getDataFormat()!=0){
                                String[] str=list.get(clos).split("/");
                                int y= Integer.parseInt(str[0]);
                                int m=Integer.parseInt(str[1]);
                                int d=Integer.parseInt(str[2]);
                                long n=timeChange(y,m,d);
                                newCell.setCellValue(n);
                                newCell.setCellStyle(styleN);
                            }else {
                                newCell.setCellValue(Double.parseDouble(list.get(clos)));
                                newCell.setCellStyle(styleN);
                            }
                            break;
                    }
                    clos++;
                }
                break;
            }
        }
        FileOutputStream out = new FileOutputStream(
                new File(path));
        workbook.write(out);
        out.close();
        System.out.println("Success");
    }
    public static long timeChange(int y,int m,int d){
        Calendar calendar = Calendar.getInstance();
        calendar.set(y,m,d);
        return calendar.getTimeInMillis()/86400000+25538;
    }
}
