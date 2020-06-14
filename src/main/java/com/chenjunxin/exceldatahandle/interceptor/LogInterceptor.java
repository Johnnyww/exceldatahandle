package com.chenjunxin.exceldatahandle.interceptor;

import com.chenjunxin.exceldatahandle.constants.LogConstants;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;

/**
 * TODO 单应用打日志替代每个方法传traceId的实现
 */
public class LogInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String traceId = request.getHeader(LogConstants.TRACE_ID);
//        if(traceId == null){
//            traceId = TraceIdUtil.getTraceId();
//        }
//        MDC.put(LogConstants.TRACE_ID, traceId);
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
//    }

}
