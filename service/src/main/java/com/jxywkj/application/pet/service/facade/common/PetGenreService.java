package com.jxywkj.application.pet.service.facade.common;

import com.jxywkj.application.pet.model.common.PetGenre;

import java.util.List;

/**
 * <p>
 * 宠物二级分类
 * </p>
 *
 * @author LiuXiangLin
 * @date 16:45 2020/4/9
 **/
public interface PetGenreService {

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.common.PetGenre>
     * @Author LiuXiangLin
     * @Description 查询所有的宠物类别
     * @Date 13:56 2019/7/13
     * @Param []
     **/
    List<PetGenre> listAll();

    /**
     * @return java.util.List<com.jxywkj.application.pet.model.common.PetGenre>
     * @Author LiuXiangLin
     * @Description 通过宠物类型查询宠物类别
     * @Date 16:41 2019/8/15
     * @Param [petTypeName]
     **/
    List<PetGenre> listPetClassifyByTypeName(String petTypeName);


    /**
     * @return com.jxywkj.application.pet.model.common.PetGenre
     * @Author LiuXiangLin
     * @Description 通过类型名称查询类型
     * @Date 16:41 2019/8/15
     * @Param [classifyName]
     **/
    PetGenre getByClassifyName(String classifyName);

    /***
     * <p>
     * 通过关键字获取数据（关键字 拼音）
     * </p>
     *
     * @param keyWord 关键字
     * @return java.util.List<com.jxywkj.application.pet.model.common.PetGenre>
     * @author LiuXiangLin
     * @date 15:50 2020/4/22
     **/
    List<PetGenre> listByKeyWord(String keyWord);
}
