package com.chenjunxin.exceldatahandle.service.impl;

import com.chenjunxin.exceldatahandle.enu.ColumnMapKeyEnum;
import com.chenjunxin.exceldatahandle.service.ExcelDataHandleService;
import com.chenjunxin.exceldatahandle.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by chenjunxin on 2018/10/18
 */
@Service
public class ExcelDataHandleServiceImpl implements ExcelDataHandleService {

    @Override
    public void handleExcel(InputStream input, OutputStream out, String fileName) throws Exception {
        List<String> titleList = new ArrayList<>();
        Map<String, Integer> columnMap;
        Map<String, Integer> resultColumnMap;
        try {
//            InputStream input = new FileInputStream("F:\\开票凭证模板.xlsx");
//            FileOutputStream out=new FileOutputStream("F:\\开票凭证模板2.xlsx");  //向F:\23.xlsx中写数据
//            Workbook workbook = new XSSFWorkbook(input);
            Workbook workbook = null;
            if (ExcelUtils.isExcel2007(fileName)) {
                workbook = new XSSFWorkbook(input);
            } else {
                workbook = new HSSFWorkbook(input);
            }

            // 通过索引读取第一张工作表
            Sheet sheet = workbook.getSheetAt(0);
            // 第一个工作表的总行数
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

            // 获取第一行的所有标题
            for (int a = 0; a < sheet.getRow(0).getPhysicalNumberOfCells(); a++) {
                Cell cell = sheet.getRow(0).getCell(a);
                if (cell != null && !cell.equals("")) {
                    titleList.add(a, cell.getStringCellValue());
                } else {
                    // 排除空值的标题列影响
                    titleList.add(a, "无意义占位值,替换空值");
                }
            }

            // 输出最终收集到的第一行的值
            System.out.println("标题行的值为:");
            titleList.forEach(w -> System.out.print(w + ","));

            columnMap = this.getColumnIndexAccordingTitleList(titleList,null);

            if(columnMap !=null && !columnMap.isEmpty()){
                List<String> paramList = new ArrayList<>();
                paramList.add(ColumnMapKeyEnum.FIRST_RESULE_CLOUMN_INDEX.getKey());
                paramList.add(ColumnMapKeyEnum.SECOND_RESULE_CLOUMN_INDEX.getKey());
                paramList.add(ColumnMapKeyEnum.THIRD_RESULE_CLOUMN_INDEX.getKey());
                paramList.add(ColumnMapKeyEnum.FOURTH_RESULE_CLOUMN_INDEX.getKey());
                resultColumnMap = this.getColumnIndexAccordingTitleList(titleList,paramList);
                // 如果sheet中存在"辅助核算种类"的列名,才进行处理
                if (columnMap.containsKey(ColumnMapKeyEnum.RULE_COLUMN_INDEX.getKey())) {
                    Integer ruleIndex = columnMap.get(ColumnMapKeyEnum.RULE_COLUMN_INDEX.getKey());
                    for (int j = 1; j < physicalNumberOfRows; j++) {
                        Row row = sheet.getRow(j);
                        System.out.println("第" + j + "行");
                        if (ruleIndex != null && ruleIndex != -1) {
                            Cell cell = row.getCell(ruleIndex);
                            String ruleString;
                            if (cell != null && !cell.equals("")) {
                                ruleString = cell.getStringCellValue();
                            } else {
                                break;
                            }
                            if (!ruleString.isEmpty()) {
                                String[] ruleArray = ruleString.split(",");
                                Iterator it = resultColumnMap.entrySet().iterator();
                                for (int m = 0; m < resultColumnMap.size(); m++) {
                                    Map.Entry entity = (Map.Entry) it.next();
//                                System.out.println("[ key = " + entity.getKey() +
//                                        ", value = " + entity.getValue() + " ]");
                                    if (m >= ruleArray.length) {
                                        break;
                                    }
                                    String currentRuleColumnName = ruleArray[m];
                                    if (currentRuleColumnName.isEmpty()) {
                                        break;
                                    } else {
                                        // 以防万一去掉收尾空格
                                        if(!columnMap.containsKey(currentRuleColumnName.trim())){
                                            throw new Exception("Excel表中不包含列名为:"+currentRuleColumnName.trim()+"的列导致在"+j+"行的数据处理中出错!");
                                        }
                                        Integer originIndex = columnMap.get(currentRuleColumnName.trim());
                                        Cell originCell = row.getCell(originIndex);
                                        String originValue = "";
                                        switch (originCell.getCellType()) {
                                            case STRING:
                                                originValue = originCell.getRichStringCellValue().getString();
                                                break;
                                            case NUMERIC:
                                                // double四舍五入，0.5进1
                                                BigDecimal bd = new BigDecimal(originCell.getNumericCellValue()).setScale(0, BigDecimal.ROUND_HALF_UP);
                                                Integer value = Integer.parseInt(bd.toString());
                                                originValue = String.valueOf(value);
                                                break;
                                            case FORMULA:
                                                try {
                                                    originValue = String.valueOf(originCell.getStringCellValue());
                                                } catch (IllegalStateException e) {
                                                    BigDecimal bd2 = new BigDecimal(originCell.getNumericCellValue()).setScale(0, BigDecimal.ROUND_HALF_UP);
                                                    Integer value2 = Integer.parseInt(bd2.toString());
                                                    originValue = String.valueOf(value2);
                                                }
                                                break;
                                            default:
                                                originValue = originCell.toString();
                                                break;
                                        }
                                        Integer resultCellIndex = (Integer) entity.getValue();
                                        Cell resultCell = row.createCell(resultCellIndex);
                                        String resultValue = originValue + ":" + currentRuleColumnName.trim();

                                        // 创建并设置字体颜色
                                        Font font = workbook.createFont();
                                        font.setColor(Font.COLOR_RED);
                                        // 创建一个单元格样式
                                        CellStyle style = workbook.createCellStyle();
                                        style.setFont(font);
                                        resultCell.setCellValue(resultValue);
                                        resultCell.setCellStyle(style);
                                        System.out.println(resultValue);
                                    }
                                }

                            }
                        }
                    }
                }
            }
            out.flush();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据标题行获取的列名得到需要处理的列的列号集合
     * 2018/12/28
     *
     * @param titleList
     */
    @Override
    public Map<String,Integer> getColumnIndexAccordingTitleList(List<String> titleList,List<String> otherNeedColumnList) {
        Map<String, Integer> columnMap = new LinkedHashMap<>();
        if(otherNeedColumnList != null && !otherNeedColumnList.isEmpty()){
            otherNeedColumnList.forEach(s -> {
                int index = titleList.indexOf(s);
                if (index != -1) {
                    columnMap.put(s, index);
                }
            });
        }
        else {
            // 如果titleList存在已经定义的规则中的列名,则获取列的列号，从0开始,并且获取index根据传入的columnMapKey添加入columnMap中
            Arrays.stream(ColumnMapKeyEnum.values()).forEach(columnMapKeyEnum -> {
                int index = titleList.indexOf(columnMapKeyEnum.getKey());
                if (index != -1) {
                    columnMap.put(columnMapKeyEnum.getKey(), index);
                }
            });
        }
        return columnMap;
    }
}
