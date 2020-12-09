package com.jxywkj.application.pet.dao.common;

import com.jxywkj.application.pet.model.common.PetSort;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 宠物一级分类
 * </p>
 *
 * @author LiuXiangLin
 * @date 14:36 2020/4/9
 **/
@Mapper
public interface PetSortMapper {

    /**
     * 根据宠物分类编号获取宠物分类
     *
     * @param petTypeName
     * @return
     */
    PetSort getPetType(String petTypeName);

    /**
     * 加载宠物分类列表
     *
     * @return
     */
    List<PetSort> listPetType();

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.common.PetSort>
     * @Author LiuXiangLin
     * @Description 查询所有
     * @Date 9:54 2019/8/15
     * @Param []
     **/
    List<PetSort> listAllType();

    /**
     * @Description: 通过编号获取
     * @Author: zxj
     * @Date: 2020/9/11 16:12
     * @param petSortNo:
     * @return: com.jxywkj.application.pet.model.common.PetSort
     **/
    PetSort getByPetSortNo(@Param("petSortNo") Integer petSortNo);
}
