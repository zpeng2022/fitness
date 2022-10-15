package com.yiie.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExcelUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);
    public void excelToShopIdList(InputStream inputStream) {
        System.out.print("excelUtils\n");
        List<String> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum() + 1;
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //得到指定的单元格
            Cell cell = row.getCell(0);
            for (int i = 1; i < rowLength; i++) {
                row = sheet.getRow(i);
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
//                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String data = cell.getStringCellValue();
                        data = data.trim();
                        if (StringUtils.isNumeric(data))
                            list.add(data);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("parse excel file error :", e);
        }
        for(String i:list){
            System.out.print(i+"\n");
        }
    }
}