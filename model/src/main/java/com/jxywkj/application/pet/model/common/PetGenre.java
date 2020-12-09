package com.jxywkj.application.pet.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Date 2019-06-19 1:10
 * @Version 1.0
 */
@Data
@ApiModel(description = "宠物类型")
public class PetGenre {
    @ApiModelProperty(value = "宠物分类")
    PetSort petSort;

    @ApiModelProperty(value = "宠物类型主键")
    Integer petGenreNo;

    @ApiModelProperty(value = "宠物类型名称")
    String petGenreName;

    @ApiModelProperty(value = "宠物图片")
    String petGenreImg;

    @ApiModelProperty(value = "拼音")
    String pinyin;

    public PetGenre(String petGenreName) {
        this.petGenreName = petGenreName;
    }

    public PetGenre() {
    }
}

