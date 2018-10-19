package com.chenjunxin.exceldatahandle.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjunxin on 2018/10/18
 */
public interface ExcelDataHandleService {


    /**
     * 批量处理得到columnMap的最后结果
     */
    void setMapValueAccordingList(List<String> titleList, Map<String,Integer> columnMap);

    /**
     * 根据传入的参数判断List是否存在对应的列,并在Map中加入相应的值
     */
    void judgeListToMapAccordingParams(List<String> titleList, Map<String,Integer> columnMap,String listColumnName,String columnMapKey);

    /**
     *  判断获取到的标题栏中是否有辅助核算1，2，3，4列
     */
    Map<String,Integer> selectResultColumnToMap(Map<String,Integer> columnMap);


     void handleExcel(InputStream input, OutputStream out,String fileName) throws Exception;
}
