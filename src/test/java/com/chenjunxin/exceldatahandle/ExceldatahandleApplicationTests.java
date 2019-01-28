package com.chenjunxin.exceldatahandle;

import com.chenjunxin.exceldatahandle.service.ExcelDataHandleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Test
    public void regexMatches() {

        // 按指定模式在字符串查找
//        String line = "This order was placed for QT3000! OK?";
        String line = "欣凯3栋1404";
//        String pattern = "(\\D*)(\\d+)(.*)";
        String pattern2 = "[^\\d]+([\\d]+).*";
        String pattern = "([^\\d]+)([\\d]+.)([\\d]+)*";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        System.out.println(m.groupCount());
        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3));
//            System.out.println("Found value: " + m.group(4));
        } else {
            System.out.println("NO MATCH");
        }
    }


    @Test
    public void testStringBuffer() {
        String a = "欣凯";
        String b = "3栋";
        Integer c = 1404;
        Integer d = 1605;
        StringBuffer stringBuffer = new StringBuffer(a);
        stringBuffer.append(b);
        int i = stringBuffer.length();
        System.out.println(stringBuffer.toString());
        System.out.println("buffer length i is :" + i);
        stringBuffer.append(c);
        int k = stringBuffer.length();
        System.out.println(stringBuffer.toString());
        System.out.println("buffer length k is :" + k);
//        stringBuffer.delete(i,k);
        stringBuffer.replace(i, k, String.valueOf(d));
        System.out.println(stringBuffer.toString());
    }

    @Test
    public void testConcatStr() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <= 50000; i++) {
            int w = i + 1000;
            list.add(w);
        }
        String diffTower = "欣凯";
        String diffBuildingInfo = "3栋";
        String meetingAddress = "欣凯3栋1404";
        StringBuffer stringBuffer = new StringBuffer(diffTower);
        stringBuffer.append(diffBuildingInfo);
        int w = stringBuffer.length();
        int k = 0;
        long t1 = System.nanoTime();
        //这里是初始字符串定义
        for (int i = 0; i <= 50000; i++) {
            //这里是字符串拼接代码
            if (meetingAddress.contains(diffTower + diffBuildingInfo + list.get(i))) {
                System.out.println(diffTower + diffBuildingInfo + list.get(i));
                System.out.println("+++++");
            }
//            if(i == 0){
//                stringBuffer.append(list.get(i));
//                k = stringBuffer.length();
//            }else {
//                stringBuffer.replace(w,k,String.valueOf(list.get(i)));
//                k = stringBuffer.length();
//            }
//
//            if (meetingAddress.contains(stringBuffer.toString() )) {
//                System.out.println(stringBuffer.toString());
//                System.out.println("+++++");
//            }
        }
        long t2 = System.nanoTime();
        System.out.println("cost:" + (t2 - t1));
        final String p = "666";
        System.out.println(p);
        LocalDate date = LocalDate.now();
        System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));
    }
}
