package com.jxywkj.application.pet.model.consign;

import com.jxywkj.application.pet.model.business.Business;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 站点评价model
 */
@Data
@ApiModel("站点服务评价实体类")
public class StationAppraise {

    @ApiModelProperty("站点评价主键")
    int appraiseNo;

    @ApiModelProperty("被评价的站点")
    Station station;

    @ApiModelProperty("评价人")
    Business business;

    @ApiModelProperty("评价星级")
    int praiseDegree;

    @ApiModelProperty("评价内容")
    String content;

    @ApiModelProperty("评价日期")
    String appraiseDate;

    public StationAppraise() {
    }

    public StationAppraise(int stationNo, String businessNo, int praiseDegree, String content, String appraiseDate) {
        Station station = new Station(stationNo);
        Business business = new Business();
        business.setBusinessNo(businessNo);
        this.station = station;
        this.business = business;
        this.praiseDegree = praiseDegree;
        this.content = content;
        this.appraiseDate = appraiseDate;
    }
}
