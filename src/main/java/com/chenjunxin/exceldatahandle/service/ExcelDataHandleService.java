package com.chenjunxin.exceldatahandle.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by chenjunxin on 2018/10/18
 */
public interface ExcelDataHandleService {

     void handleExcel(InputStream input, OutputStream out,String fileName) throws Exception;


    /**
     * 根据标题行获取的列名得到需要处理的列的列号集合
     * 2018/12/28
     */
    Map<String,Integer> getColumnIndexAccordingTitleList(List<String> titleList,List<String> otherNeedColumnList);
}
