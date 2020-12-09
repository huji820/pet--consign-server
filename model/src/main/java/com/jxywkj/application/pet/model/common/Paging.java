package com.jxywkj.application.pet.model.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @ClassName Paging
 * @Author LiuXiangLin
 * @Date 2019/9/28 9:18
 * @Version 1.0
 **/
@Data
@ApiModel(description = "分页数据")
public class Paging<T> {
    List<T> data;
    int offset;
    int limit;
    int totalCount;
    T param;
}
