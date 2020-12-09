package com.jxywkj.application.pet.dao.common;

import com.jxywkj.application.pet.model.common.BlackList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BlackListMapper
 * @date 2020/1/8 9:11
 **/
@Mapper
public interface BlackListMapper {
    /***
     * <p>
     * 保存数据
     * </p>
     *
     * @param blackLists 黑名单对象
     * @return int
     * @author LiuXiangLin
     * @date 9:12 2020/1/8
     **/
    int save(@Param("blackLists") List<BlackList> blackLists);

    /**
     * <p>
     * 查询所有的黑民单列表
     * </p>
     *
     * @param offset 排除条数
     * @param limit  显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.common.BlackList>
     * @author LiuXiangLin
     * @date 9:12 2020/1/8
     **/
    List<BlackList> listAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * <p>
     * 通过电话号码删除数据
     * </p>
     *
     * @param phone 电话号码
     * @return int
     * @author LiuXiangLin
     * @date 9:28 2020/1/8
     **/
    int deleteByPhone(String phone);

    /**
     * <p>
     * 通过电话号码查询
     * </p>
     *
     * @param phone 电话号码
     * @return com.jxywkj.application.pet.model.common.BlackList
     * @author LiuXiangLin
     * @date 10:44 2020/1/8
     **/
    BlackList getByPhone(@Param("phone") String phone);
}
