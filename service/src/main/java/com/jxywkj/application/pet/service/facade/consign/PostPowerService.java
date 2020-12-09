package com.jxywkj.application.pet.service.facade.consign;

import com.jxywkj.application.pet.model.common.PostPower;

import java.util.List;


/**
 * @ClassName PostPowerService
 * @Description 角色权限
 * @Author LiuXiangLin
 * @Date 2019/9/25 11:29
 * @Version 1.0
 **/
public interface PostPowerService {
    /**
     * @Author LiuXiangLin
     * @Description 添加一条数据
     * @Date 11:30 2019/9/25
     * @Param [postPower]
     * @return int
     **/
    int saveOrUpdate(PostPower postPower);

    /**
     * @Author LiuXiangLin
     * @Description 通过角色获取数据
     * @Date 14:22 2019/9/25
     * @Param [postNo]
     * @return com.jxywkj.application.pet.model.common.PostPower
     **/
    List<PostPower> listByPostNo(int postNo);

    /**
     * @Author LiuXiangLin
     * @Description 通过角色编号查询所有的字段名
     * @Date 15:41 2019/9/25
     * @Param [postNo]
     * @return java.util.List<java.lang.String>
     **/
    List<String> listFieldByPostNo(int postNo);
}
