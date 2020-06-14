package com.chenjunxin.exceldatahandle.utils;

/**
 * Created by chenjunxin on 2018/10/18
 */
public class ExcelUtils {

    /**
     * 判断是否2003的excel，返回true是2003
     *
     * @param filePath
     * @return boolean
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 判断是否2007的excel，返回true是2007
     *
     * @param filePath
     * @return boolean
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 根据文件名后缀判断是否是EXCEL文件
     *
     * @param filePath
     * @return boolean
     */
    public static boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            return false;
        }
        return true;
    }

}
