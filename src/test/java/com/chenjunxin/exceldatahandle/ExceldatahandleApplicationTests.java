package com.chenjunxin.exceldatahandle;

import com.chenjunxin.exceldatahandle.enu.ColumnMapKeyEnum;
import com.chenjunxin.exceldatahandle.service.ExcelDataHandleService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExceldatahandleApplicationTests {

    @Autowired
    private ExcelDataHandleService excelDataHandleService;

    @Test
    public void contextLoads() {
    }


//    @Test
//    public void testExcel2003NoModel() {
//        String SUFFIX_2003 = ".xls";
//        String SUFFIX_2007 = ".xlsx";
//        List<String> titleList = new ArrayList<>();
//        Map<String, Integer> columnMap = new HashMap<>();
//        try {
////            InputStream input = new FileInputStream("F:\\23.xlsx");
////            FileOutputStream out=new FileOutputStream("F:\\24.xlsx");  //向F:\23.xlsx中写数据
//            InputStream input = new FileInputStream("F:\\开票凭证模板.xlsx");
//            FileOutputStream out = new FileOutputStream("F:\\开票凭证模板2.xlsx");  //向F:\23.xlsx中写数据
//            Workbook workbook = new XSSFWorkbook(input);
//            // 通过索引读取第一张工作表
//            Sheet sheet = workbook.getSheetAt(0);
//            //一个sheet有多少行
//            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
//
//            //获取第一行标题
//            for (int a = 0; a < sheet.getRow(0).getPhysicalNumberOfCells(); a++) {
//                Cell cell = sheet.getRow(0).getCell(a);
//                if (cell != null && !cell.equals("")) {
//                    titleList.add(a, cell.getStringCellValue());
//                } else {
//                    titleList.add(a, "无意义占位值,替换空值");
//                }
//            }
//            titleList.forEach(w -> System.out.print(w + ","));
//
//            excelDataHandleService.setMapValueAccordingList(titleList, columnMap);
//
//            Map<String, Integer> resultColumnMap = excelDataHandleService.selectResultColumnToMap(columnMap);
//
//            // 如果sheet中存在"辅助核算种类"的列名，才进行处理
//            if (!columnMap.isEmpty() && columnMap.containsKey(ColumnMapKeyEnum.RULE_COLUMN_INDEX.getKey())) {
//                Integer ruleIndex = columnMap.get(ColumnMapKeyEnum.RULE_COLUMN_INDEX.getKey());
//                for (int j = 1; j < physicalNumberOfRows; j++) {
//                    Row row = sheet.getRow(j);
//                    if (ruleIndex != null && ruleIndex != -1) {
//                        Cell cell = row.getCell(ruleIndex);
//                        String ruleString = cell.getStringCellValue();
//                        if (!ruleString.isEmpty()) {
//                            String[] ruleArray = ruleString.split(",");
//                            Iterator it = resultColumnMap.entrySet().iterator();
//                            for (int m = 0; m < resultColumnMap.size(); m++) {
//                                Map.Entry entity = (Map.Entry) it.next();
////                                System.out.println("[ key = " + entity.getKey() +
////                                        ", value = " + entity.getValue() + " ]");
//                                if (m >= ruleArray.length) {
//                                    break;
//                                }
//                                String currentRuleColumnName = ruleArray[m];
//                                if (currentRuleColumnName.isEmpty()) {
//                                    break;
//                                } else {
//                                    // 以防万一去掉收尾空格
//                                    Integer originIndex = columnMap.get(currentRuleColumnName.trim());
//                                    Cell originCell = row.getCell(originIndex);
//                                    String originValue = "";
//                                    switch (originCell.getCellTypeEnum()) {
//                                        case STRING:
//                                            originValue = originCell.getRichStringCellValue().getString();
//                                            break;
//                                        case NUMERIC:
//                                            // double四舍五入，0.5进1
//                                            BigDecimal bd = new BigDecimal(originCell.getNumericCellValue()).setScale(0, BigDecimal.ROUND_HALF_UP);
//                                            Integer value = Integer.parseInt(bd.toString());
//                                            originValue = String.valueOf(value);
//                                            break;
//                                        case FORMULA:
//                                            try {
//                                                originValue = String.valueOf(originCell.getStringCellValue());
//                                            } catch (IllegalStateException e) {
//                                                BigDecimal bd2 = new BigDecimal(originCell.getNumericCellValue()).setScale(0, BigDecimal.ROUND_HALF_UP);
//                                                Integer value2 = Integer.parseInt(bd2.toString());
//                                                originValue = String.valueOf(value2);
//                                            }
//                                            break;
//                                        default:
//                                            originValue = originCell.toString();
//                                            break;
//                                    }
//                                    Integer resultCellIndex = (Integer) entity.getValue();
//                                    Cell resultCell = row.createCell(resultCellIndex);
//                                    String resultValue = originValue + ":" + currentRuleColumnName.trim();
//                                    resultCell.setCellValue(resultValue);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            out.flush();
//            workbook.write(out);
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
}
