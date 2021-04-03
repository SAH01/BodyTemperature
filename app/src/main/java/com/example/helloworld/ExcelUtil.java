package com.example.helloworld;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {
    private static WritableFont arial14font = null;
    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";
    private static MyDBHelper mDBHelper;
    private static DBOpenHelperLogin mDBOpenHelperLogin;

    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */
    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Excel
     *
     * @param fileName 导出excel存放的地址（目录）
     */
    /*public static void initExcel(String filePath, String fileName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            //设置表格的名字
            WritableSheet sheet = workbook.createSheet("", 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, fileName));
            for (int col = 0; col < colName.length; col ++) {
                sheet.addCell(new Label(col, 0, colName[col]));
            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
*/
    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel( String fileName) {
            WritableWorkbook writebook = null;
            File in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in=new File(fileName);
                Workbook workbook = Workbook.getWorkbook(in);
                Log.v("uuuuuuuu","到这里了！！");
                writebook = Workbook.createWorkbook(new File(fileName), workbook);

                WritableSheet wsheet = writebook.getSheet(0);

                Label danwei = new Label(1, 1, "石家庄铁道大学");
                wsheet.addCell(danwei);
                Label riqi = new Label(5, 1, "2021-03-11");
                wsheet.addCell(riqi);
                Label username=new Label(1,2,"杨传伟");
                wsheet.addCell(username);

                Label userid=new Label(5,2,"20194074");
                wsheet.addCell(userid);

                Label userphone = new Label(5,3,"15284206891");
                wsheet.addCell(userphone);

                Label now = new Label(1,3,"健康");
                wsheet.addCell(now);
                for(int col=1;  col<=3; col++){
                    for(int row=6   ;row<=19;row++){
                            Label l1=new Label(col,row,"36.2℃");
                            wsheet.addCell(l1);
                            Log.v("uuuuuuuu","到l1这里了！！");
                        if(col==2)
                        {
                            Label l2=new Label(col,row,"无");
                            wsheet.addCell(l2);
                            Log.v("uuuuuuuu","到l2这里了！！");
                        }
                        if(col==3)
                        {
                            Label l3=new Label(col,row,"中国河北省邯郸市武安市午汲镇");
                            wsheet.addCell(l3);
                            Log.v("uuuuuuuu","到l3这里了！！");
                        }
                    }
                }
                writebook.write();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }