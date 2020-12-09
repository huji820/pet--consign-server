package com.jxywkj.application.pet.dao.common;

import com.jxywkj.application.pet.model.common.PetGenre;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 宠物二级分类
 * </p>
 *
 * @author LiuXiangLin
 * @date 14:44 2020/4/9
 **/
@Mapper
public interface PetGenreMapper {
    /**
     * @return java.util.List<com.jxywkj.application.pet.model.common.PetGenre>
     * @Author LiuXiangLin
     * @Description 查询所有的类型
     * @Date 14:10 2019/7/13
     * @Param []
     **/
    List<PetGenre> listAll();


    /**
     * @return java.util.List<com.jxywkj.application.pet.model.common.PetGenre>
     * @Author LiuXiangLin
     * @Description 通过类型名称查询类别
     * @Date 16:39 2019/8/15
     * @Param [petTypeName]
     **/
    List<PetGenre> listPetClassifyByTypeName(String petTypeName);

    /**
     * @return com.jxywkj.application.pet.model.common.PetGenre
     * @Author LiuXiangLin
     * @Description 通过宠物类型查询数据
     * @Date 13:57 2019/7/17
     * @Param [classifyName]
     **/
    PetGenre getByClassifyName(@Param("classifyName") String classifyName);

    /**
     * <p>
     * 通过关键字查询
     * </p>
     *
     * @param keyWord 关键字
     * @return java.util.List<com.jxywkj.application.pet.model.common.PetGenre>
     * @author LiuXiangLin
     * @date 10:35 2020/4/22
     **/
    List<PetGenre> listByKeyWord(@Param("keyWord") String keyWord);
}
