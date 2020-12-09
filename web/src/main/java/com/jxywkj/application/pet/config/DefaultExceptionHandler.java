package com.jxywkj.application.pet.config;

import com.jxywkj.application.pet.common.enums.Code;
import com.jxywkj.application.pet.common.utils.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Date 16:24 2019/9/6
 * Param
 * return
 **/
@ControllerAdvice
public class DefaultExceptionHandler {
    /**
     * 全局异常捕捉处理
     *
     * @param e
     * @return JsonResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult defaultExceptionHandler(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        return JsonResult.error(Code.ERROR, e.getMessage());
    }
}
