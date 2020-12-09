package com.jxywkj.application.pet.model.consign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName Post
 * @Description: 岗位
 * @Author Aze
 * @Date 2019/7/17 17:02
 * @Version 1.0
 **/
@Data
@ApiModel(description = "员工岗位")
public class Post {
    @ApiModelProperty(value = "岗位编号")
    Integer postNo;

    @ApiModelProperty(value = "岗位名称")
    String postName;

    @ApiModelProperty(value = "网点")
    Station station;

    public Post(int postNo) {
        this.postNo = postNo;
    }

    public Post() {
    }

    public Post(int postNo, String postName) {
        this.postNo = postNo;
        this.postName = postName;
    }

}
