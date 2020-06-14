package com.chenjunxin.exceldatahandle.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class LogController {
    private static final Logger log = LoggerFactory.getLogger(LogController.class);

    @RequestMapping("log")
    @ResponseStatus(HttpStatus.OK)
    public String helloString(){
        log.trace("trace");
        log.debug("debug");
        log.warn("warn");
        log.info("info");
        log.error("error");
        return "Hello Spring Boot";
    }
}
