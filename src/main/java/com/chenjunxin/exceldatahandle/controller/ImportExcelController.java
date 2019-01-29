package com.chenjunxin.exceldatahandle.controller;

import com.chenjunxin.exceldatahandle.service.ExcelDataHandleService;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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

/**
 * Created by chenjunxin on 2018/10/18
 */
@Controller
@RequestMapping
public class ImportExcelController {

    private final Logger log = LoggerFactory.getLogger(ImportExcelController.class);

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
        try {
            fis = uploadFile.getInputStream();
            excelFileAddress = uploadFileUrl + uploadFile.getOriginalFilename();
            outputStream = new FileOutputStream(excelFileAddress);
            excelDataHandleService.handleExcel(fis, outputStream, uploadFile.getOriginalFilename());
            map.put("excelFileAddress", uploadFile.getOriginalFilename());
            map.put("successFlag","true");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("successFlag","false");
            map.put("errorInfo",e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("successFlag","false");
            map.put("errorInfo",e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    @RequestMapping(value = "/downloadFileAction", method = RequestMethod.POST)
    public void downloadFileAction(HttpServletRequest request, HttpServletResponse response, @RequestParam("downFileAddress") String downFileAddress) {
        if (downFileAddress == null || downFileAddress.isEmpty()) {
            return;
        }
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
            File file = new File(downloadFileUrl + downFileAddress);
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(downFileAddress.getBytes("gb2312"), "ISO8859-1"));
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
