package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.AddedVolumeCage;
import com.jxywkj.application.pet.model.consign.dto.AddedVolumeCageDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiuXiangLin
 * @version 1.0
 * @className AddedVolumeCageMapper
 * @date 2019/10/21 11:52
 **/
@Component
public interface AddedVolumeCageMapper {

    /**
     * 通过运输路线查询笼具
     *
     * @param transportNo 运输路线编号
     * @return java.util.List<com.jxywkj.application.pet.model.consign.AddedVolumeCage>
     * @author LiuXiangLin
     * @date 11:53 2019/10/21
     **/
    List<AddedVolumeCage> listByTransportNo(@Param("transportNo") int transportNo,@Param("stationNo")int stationNo);

    /**
     * 通过运输录像编号删除数据
     *
     * @param transportNo 运输路线编号
     * @return int
     * @author LiuXiangLin
     * @date 16:32 2019/10/21
     **/
    int deleteByTransportNo(@Param("transportNo") int transportNo);

    /**
     * 保存多条数据
     *
     * @param addedVolumeCageDtoList 数据列表
     * @return int
     * @author LiuXiangLin
     * @date 16:36 2019/10/21
     **/
    int saveList(@Param("addedVolumeCageDtoList") List<AddedVolumeCageDTO> addedVolumeCageDtoList);
}
