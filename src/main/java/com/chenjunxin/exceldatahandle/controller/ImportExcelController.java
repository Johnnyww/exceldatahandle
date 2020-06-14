package com.chenjunxin.exceldatahandle.controller;

import com.chenjunxin.exceldatahandle.service.ExcelDataHandleService;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by chenjunxin on 2018/10/18
 */
@Controller
@RequestMapping
public class ImportExcelController {

    private static final Logger logger = LoggerFactory.getLogger(ImportExcelController.class);

    @Autowired
    ExcelDataHandleService excelDataHandleService;

    @Value("${exceldatahandle.uploadFile.url}")
    private String uploadFileUrl;

    @Value("${exceldatahandle.downloadFile.url}")
    private String downloadFileUrl;

    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/uploadFileAction", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadFileAction(@RequestParam("uploadFile") MultipartFile uploadFile, @RequestParam("id") Long id) {
        Map<String, String> map = new HashMap<>();
        InputStream fis = null;
        OutputStream outputStream = null;
        String excelFileAddress = "";
        // 生成日志跟踪ID，便于跟踪单次请求
        String traceId = UUID.randomUUID().toString();
        StringBuilder strBuilder = new StringBuilder(traceId);
        strBuilder.append(" ");
        strBuilder.append(uploadFile.getOriginalFilename());
        try {
            fis = uploadFile.getInputStream();
            excelFileAddress = uploadFileUrl + uploadFile.getOriginalFilename();
            outputStream = new FileOutputStream(excelFileAddress);
            excelDataHandleService.handleExcel(fis, outputStream, uploadFile.getOriginalFilename(),traceId);
            map.put("excelFileAddress", uploadFile.getOriginalFilename());
            map.put("successFlag","true");
            strBuilder.append(": 上传文件处理成功");
            logger.info(strBuilder.toString());
        } catch (Exception e) {
            strBuilder.append(": 上传文件处理出现异常");
            logger.error(strBuilder.toString(),e);
            map.put("successFlag","false");
            map.put("errorInfo",e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // StringBuilder重用 清空数据
                    strBuilder.delete(0,strBuilder.length());
                    strBuilder.append(traceId);
                    strBuilder.append(": InputStream close have exception");
                    logger.error(strBuilder.toString(),e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // StringBuilder重用 清空数据
                    strBuilder.delete(0,strBuilder.length());
                    strBuilder.append(traceId);
                    strBuilder.append(": FileOutputStream close have exception");
                    logger.error(strBuilder.toString(),e);
                }
            }
        }
        return map;
    }

    @RequestMapping(value = "/downloadFileAction", method = RequestMethod.POST)
    public void downloadFileAction(HttpServletRequest request, HttpServletResponse response, @RequestParam("downFileAddress") String downFileAddress) {
        if (StringUtils.isEmpty(downFileAddress)) {
            return;
        }
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        StringBuilder strBuilder = new StringBuilder(downloadFileUrl + downFileAddress);
        String downloadPath = strBuilder.toString();
        try {
            File file = new File(downloadPath);
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(downFileAddress.getBytes("gb2312"), "ISO8859-1"));
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            strBuilder.append(": 下载文件出现异常");
            logger.error(strBuilder.toString(),e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    String errorStr = downloadPath + ": FileInputStream close have exception";
                    logger.error(errorStr,e);
                }
            }
        }
    }

}
