package com.jxywkj.application.pet.service.facade.common;

import com.jxywkj.application.pet.model.common.BlackList;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className BlackListService
 * @date 2020/1/8 9:20
 **/
public interface BlackListService {
    /**
     * <p>
     * 新增多条数据
     * </p>
     *
     * @param phoneList 电话号码列表
     * @return int
     * @author LiuXiangLin
     * @date 9:21 2020/1/8
     **/
    int saveList(List<String> phoneList);

    /**
     * <p>
     * 查询所有数据
     * </p>
     *
     * @param offset 排除条数
     * @param limit  显示条数
     * @return java.util.List<com.jxywkj.application.pet.model.common.BlackList>
     * @author LiuXiangLin
     * @date 9:21 2020/1/8
     **/
    List<BlackList> listAll(int offset, int limit);

    /**
     * <p>
     * 通过电话号码删除数据
     * </p>
     *
     * @param phone 电话号码
     * @return int
     * @author LiuXiangLin
     * @date 9:22 2020/1/8
     **/
    int deleteByPhone(String phone);

    /**
     * <p>
     * 通过电话号码查询数据
     * </p>
     *
     * @param phone 电话号码
     * @return com.jxywkj.application.pet.model.common.BlackList
     * @author LiuXiangLin
     * @date 10:43 2020/1/8
     **/
    BlackList getByPhone(String phone);
}
