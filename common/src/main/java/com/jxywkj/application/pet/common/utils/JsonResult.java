package com.jxywkj.application.pet.common.utils;

import com.jxywkj.application.pet.common.enums.Code;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LiWenHao
 * @date 2018/5/22 16:31
 */
@Data
@ApiModel(value = "Json返回值")
public class JsonResult<T> {

    @ApiModelProperty(value = "错误代码")
    private int code;
    private String message;
    private T data;

    public static <T> JsonResult ok(Code code, T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(code.getCode());
        jsonResult.setMessage(code.getMessage());
        jsonResult.setData(data);
        return jsonResult;
    }

    public static <T> JsonResult ok(String message) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(200);
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static JsonResult error(Code code) {
        JsonResult jsonResult = new JsonResult<>();
        jsonResult.setCode(code.getCode());
        jsonResult.setMessage(code.getMessage());
        return jsonResult;
    }

    public static JsonResult error(Code code, String message) {
        JsonResult jsonResult = new JsonResult<>();
        jsonResult.setCode(code.getCode());
        jsonResult.setMessage(message);
        return jsonResult;
    }

    public static JsonResult success() {
        JsonResult jsonResult = new JsonResult<>();
        jsonResult.setCode(200);
        jsonResult.setMessage("请求成功");
        return jsonResult;
    }

    public static <T> JsonResult success(T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setCode(200);
        jsonResult.setMessage("请求成功");
        jsonResult.setData(data);
        return jsonResult;
    }

    public static <T> JsonResult success(Code code) {
        JsonResult jsonResult = new JsonResult<>();
        jsonResult.setCode(code.getCode());
        jsonResult.setMessage(code.getMessage());
        return jsonResult;
    }

    public static JsonResult failed() {
        JsonResult jsonResult = new JsonResult<>();
        jsonResult.setCode(500);
        jsonResult.setMessage("系统异常");
        return jsonResult;
    }

}