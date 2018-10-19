package com.chenjunxin.exceldatahandle.service.impl;

import com.chenjunxin.exceldatahandle.enu.CloumnEnum;
import com.chenjunxin.exceldatahandle.enu.ColumnMapKeyEnum;
import com.chenjunxin.exceldatahandle.service.ExcelDataHandleService;
import com.chenjunxin.exceldatahandle.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.net.FileNameMap;
import java.util.*;

/**
 * Created by chenjunxin on 2018/10/18
 */
@Service
public class ExcelDataHandleServiceImpl implements ExcelDataHandleService {


    /**
     * 批量处理得到columnMap的最后结果
     *
     * @param titleList
     * @param columnMap
     */
    @Override
    public void setMapValueAccordingList(List<String> titleList, Map<String, Integer> columnMap) {
        // 如果存在规则列,则获取列数，从0开始
        this.judgeListToMapAccordingParams(titleList,columnMap, CloumnEnum.RULE_COLUMN_NAME.getMessage(),
                ColumnMapKeyEnum.RULE_COLUMN_INDEX.getKey());

        // 如果存在部门列,则获取列数，从0开始
        this.judgeListToMapAccordingParams(titleList,columnMap,CloumnEnum.DEPART_COLUMN_NAME.getMessage(),
                ColumnMapKeyEnum.DEPART_COLUMN_INDEX.getKey());

        // 如果存在供应商档案列,则获取列数，从0开始
        this.judgeListToMapAccordingParams(titleList,columnMap,CloumnEnum.VENDOR_CLOUMN_NAME.getMessage(),
                ColumnMapKeyEnum.VENDOR_CLOUMN_INDEX.getKey());

        // 如果存在项目档案列,则获取列数，从0开始
        this.judgeListToMapAccordingParams(titleList,columnMap,CloumnEnum.PROJECT_CLOUMN_NAME.getMessage(),
                ColumnMapKeyEnum.PROJECT_CLOUMN_INDEX.getKey());

        // 如果存在行业列,则获取列数，从0开始
        this.judgeListToMapAccordingParams(titleList,columnMap,CloumnEnum.INDUSTRY_CLOUMN_NAME.getMessage(),
                ColumnMapKeyEnum.INDUSTRY_CLOUMN_INDEX.getKey());

        // 如果存在客户档案列,则获取列数，从0开始
        this.judgeListToMapAccordingParams(titleList,columnMap,CloumnEnum.CUSTOMER_PROFILE_CLOUMN_NAME.getMessage(),
                ColumnMapKeyEnum.CUSTOMER_PROFILE_CLOUMN_INDEX.getKey());

        // 如果存在辅助核算1列,则获取列数，从0开始
        this.judgeListToMapAccordingParams(titleList,columnMap,CloumnEnum.FIRST_RESULE_CLOUMN_NAME.getMessage(),
                ColumnMapKeyEnum.FIRST_RESULE_CLOUMN_INDEX.getKey());

        // 如果存在辅助核算2列,则获取列数，从0开始
        this.judgeListToMapAccordingParams(titleList,columnMap,CloumnEnum.SECOND_RESULE_CLOUMN_NAME.getMessage(),
                ColumnMapKeyEnum.SECOND_RESULE_CLOUMN_INDEX.getKey());

        // 如果存在辅助核算3列,则获取列数，从0开始
        this.judgeListToMapAccordingParams(titleList,columnMap,CloumnEnum.THIRD_RESULE_CLOUMN_NAME.getMessage(),
                ColumnMapKeyEnum.THIRD_RESULE_CLOUMN_INDEX.getKey());

        // 如果存在辅助核算4列,则获取列数，从0开始
        this.judgeListToMapAccordingParams(titleList,columnMap,CloumnEnum.FOURTH_RESULE_CLOUMN_NAME.getMessage(),
                ColumnMapKeyEnum.FOURTH_RESULE_CLOUMN_INDEX.getKey());

    }

    /**
     * 根据传入的参数判断List是否存在对应的列,并在Map中加入相应的值
     *
     * @param titleList
     * @param columnMap
     * @param listColumnName
     * @param columnMapKey
     */
    @Override
    public void judgeListToMapAccordingParams(List<String> titleList, Map<String, Integer> columnMap, String listColumnName, String columnMapKey) {
        // 如果titleList存在listColumnName列,则获取列数，从0开始,并且获取index根据传入的columnMapKey添加入columnMap中
        if (titleList.contains(listColumnName)){
            int index = titleList.indexOf(listColumnName);
            if (index != -1) {
                columnMap.put(columnMapKey, index);
            }
        }
    }

    /**
     * 判断获取到的标题栏中是否有辅助核算1，2，3，4列
     *
     * @param columnMap
     */
    @Override
    public Map<String, Integer> selectResultColumnToMap(Map<String, Integer> columnMap) {
        Map<String,Integer> resultColumnMap = new LinkedHashMap<>();
        if (columnMap.containsKey(ColumnMapKeyEnum.FIRST_RESULE_CLOUMN_INDEX.getKey())){
            resultColumnMap.put(ColumnMapKeyEnum.FIRST_RESULE_CLOUMN_INDEX.getKey(),
                    columnMap.get(ColumnMapKeyEnum.FIRST_RESULE_CLOUMN_INDEX.getKey()));
        }
        if (columnMap.containsKey(ColumnMapKeyEnum.SECOND_RESULE_CLOUMN_INDEX.getKey())){
            resultColumnMap.put(ColumnMapKeyEnum.SECOND_RESULE_CLOUMN_INDEX.getKey(),
                    columnMap.get(ColumnMapKeyEnum.SECOND_RESULE_CLOUMN_INDEX.getKey()));
        }
        if (columnMap.containsKey(ColumnMapKeyEnum.THIRD_RESULE_CLOUMN_INDEX.getKey())){
            resultColumnMap.put(ColumnMapKeyEnum.THIRD_RESULE_CLOUMN_INDEX.getKey(),
                    columnMap.get(ColumnMapKeyEnum.THIRD_RESULE_CLOUMN_INDEX.getKey()));
        }
        if (columnMap.containsKey(ColumnMapKeyEnum.FOURTH_RESULE_CLOUMN_INDEX.getKey())){
            resultColumnMap.put(ColumnMapKeyEnum.FOURTH_RESULE_CLOUMN_INDEX.getKey(),
                    columnMap.get(ColumnMapKeyEnum.FOURTH_RESULE_CLOUMN_INDEX.getKey()));
        }
        return resultColumnMap;
    }

@Override
    public void handleExcel(InputStream input, OutputStream out, String fileName) throws Exception{
        List<String> titleList = new ArrayList<>();
        Map<String, Integer> columnMap = new HashMap<>();
        try {
//            InputStream input = new FileInputStream("F:\\23.xlsx");
//            FileOutputStream out=new FileOutputStream("F:\\24.xlsx");  //向F:\23.xlsx中写数据
//            InputStream input = new FileInputStream("F:\\开票凭证模板.xlsx");
//            FileOutputStream out=new FileOutputStream("F:\\开票凭证模板2.xlsx");  //向F:\23.xlsx中写数据
//            Workbook workbook = new XSSFWorkbook(input);
            Workbook workbook = null;
            if(ExcelUtils.isExcel2007(fileName)){
                workbook = new XSSFWorkbook(input);
            }else {
                workbook = new HSSFWorkbook(input);
            }

            // 通过索引读取第一张工作表
            Sheet sheet = workbook.getSheetAt(0);
            //一个sheet有多少行
            int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

            //获取第一行标题
            for (int a = 0; a < sheet.getRow(0).getPhysicalNumberOfCells(); a++) {
                Cell cell = sheet.getRow(0).getCell(a);
                if(cell != null && !cell.equals("")){
                    titleList.add(a, cell.getStringCellValue());
                }else{
                    titleList.add(a,"无意义占位值,替换空值");
                }
            }
            titleList.forEach(w -> System.out.print(w + ","));

            this.setMapValueAccordingList(titleList, columnMap);

            Map<String, Integer> resultColumnMap = this.selectResultColumnToMap(columnMap);

            // 如果sheet中存在"辅助核算种类"的列名，才进行处理
            if (!columnMap.isEmpty() && columnMap.containsKey(ColumnMapKeyEnum.RULE_COLUMN_INDEX.getKey())) {
                Integer ruleIndex = columnMap.get(ColumnMapKeyEnum.RULE_COLUMN_INDEX.getKey());
                for (int j = 1; j < physicalNumberOfRows; j++) {
                    Row row = sheet.getRow(j);
                    System.out.println("第"+j+"行");
                    if (ruleIndex != null && ruleIndex != -1) {
                        Cell cell = row.getCell(ruleIndex);
                        String ruleString;
                        if(cell != null && !cell.equals("")){
                            ruleString= cell.getStringCellValue();
                        }else{
                            break;
                        }
                        if (!ruleString.isEmpty()) {
                            String[] ruleArray = ruleString.split(",");
                            Iterator it = resultColumnMap.entrySet().iterator();
                            for (int m = 0; m < resultColumnMap.size(); m++) {
                                Map.Entry entity = (Map.Entry) it.next();
//                                System.out.println("[ key = " + entity.getKey() +
//                                        ", value = " + entity.getValue() + " ]");
                                if(m >= ruleArray.length){
                                    break;
                                }
                                String currentRuleColumnName = ruleArray[m];
                                if (currentRuleColumnName.isEmpty()) {
                                    break;
                                } else {
                                    // 以防万一去掉收尾空格
                                    Integer originIndex = columnMap.get(currentRuleColumnName.trim());
                                    Cell originCell = row.getCell(originIndex);
                                    String originValue = "";
                                    switch (originCell.getCellTypeEnum()) {
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
//                                    Font fontStyle = workbook.createFont();

                                    //设置字体颜色
//                                    fontStyle.setColor(Font.COLOR_RED);
//                                    CellStyle originCellStyle = resultCell.getCellStyle();
//                                    resultCell.getCellStyle().setFont(fontStyle);
                                    resultCell.setCellValue(resultValue);
//                                    resultCell.setCellStyle(originCellStyle);
                                    System.out.println(resultValue);
                                }
                            }
                        }
                    }
                }
            }
            out.flush();
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
