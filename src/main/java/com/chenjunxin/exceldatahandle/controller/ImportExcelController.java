package com.chenjunxin.exceldatahandle.controller;

import com.chenjunxin.exceldatahandle.service.ExcelDataHandleService;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
//            String excelFileAddress = "/media/uploadfile/" + uploadFile.getOriginalFilename();
//             excelFileAddress = "F:\\upload\\" + uploadFile.getOriginalFilename();
            excelFileAddress = "/media/uploadfile/" + uploadFile.getOriginalFilename();
            outputStream = new FileOutputStream(excelFileAddress);
            excelDataHandleService.handleExcel(fis, outputStream, uploadFile.getOriginalFilename());
            map.put("excelFileAddress", uploadFile.getOriginalFilename());
//            IOUtils.copy(fis, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
//        response.addHeader("Content-Disposition",
//                "attachment;filename=" + new String(name.getBytes("UTF-8"), "ISO8859-1"));
//        response.setContentType("application/octet-stream");
//        OutputStream toClient = null;
//        toClient = new BufferedOutputStream(response.getOutputStream());
//        ExcelUtil.exportExcel(findAll, str, templatePath, response.getOutputStream());
//        toClient.flush();
//        response.getOutputStream().close();
        if (downFileAddress == null || downFileAddress.isEmpty()) {
            return;
        }
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        try {
//            File file = new File("F:\\upload\\" + downFileAddress);
            File file = new File("/media/uploadfile/" + downFileAddress);
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
