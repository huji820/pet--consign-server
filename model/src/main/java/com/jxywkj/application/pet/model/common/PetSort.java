package com.jxywkj.application.pet.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2019-06-19 1:10
 * @Version 1.0
 */
@Data
@ApiModel(description = "宠物种类")
public class PetSort {
    @ApiModelProperty(value = "宠物分类主键")
    Integer petSortNo;

    @ApiModelProperty(value = "宠物分类名称")
    String petSortName;

    @ApiModelProperty(value = "分类图片")
    String petSortImg;

    @ApiModelProperty(value = "拼音")
    String pinyin;

    @ApiModelProperty(value = "宠物类别")
    List<PetGenre> petGenreList;

    public PetSort(String petSortName) {
        this.petSortName = petSortName;
    }

    public PetSort() {
    }
}
