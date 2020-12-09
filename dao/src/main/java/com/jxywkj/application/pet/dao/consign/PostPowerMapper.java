package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.common.PostPower;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName PostPowerMapper
 * @Description
 * @Author LiuXiangLin
 * @Date 2019/9/25 11:16
 * @Version 1.0
 **/
@Mapper
public interface PostPowerMapper {
    /**
     * @return int
     * @Author LiuXiangLin
     * @Description 新增或者修改数据
     * @Date 11:17 2019/9/25
     * @Param [postPower]
     **/
    int saveOrUpdate(@Param("postPower") PostPower postPower);

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.common.PostPower>
     * @Author LiuXiangLin
     * @Description 通过角色获取数据
     * @Date 14:24 2019/9/25
     * @Param [postNo]
     **/
    List<PostPower> listByPostNo(@Param("postNo") int postNo);

    /**
     * @Author LiuXiangLin
     * @Description 通过角色编号查询所有的字段
     * @Date 15:42 2019/9/25
     * @Param [postNo]
     * @return java.util.List<java.lang.String>
     **/
    List<String> listFieldsByPostNo(@Param("postNo") int postNo);
}
