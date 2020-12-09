package com.jxywkj.application.pet.service.facade.common;

import com.jxywkj.application.pet.model.common.PetSort;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 宠物一级分类
 * </p>
 *
 * @author LiuXiangLin
 * @date 14:40 2020/4/9
 **/
public interface PetSortService {

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
    List<String> listPetTypeName();
    
    /**
     * @Author LiuXiangLin
     * @Description 查询所有的宠物类型
     * @Date 9:53 2019/8/15
     * @Param []
     * @return java.util.List<com.jxywkj.application.pet.model.common.PetSort>
     **/
    List<PetSort> listAllType();

    /**
     * @Description: 通过编号获取
     * @Author: zxj
     * @Date: 2020/9/11 16:12
     * @param petSortNo:
     * @return: com.jxywkj.application.pet.model.common.PetSort
     **/
    PetSort getByPetSortNo(Integer petSortNo);
}
