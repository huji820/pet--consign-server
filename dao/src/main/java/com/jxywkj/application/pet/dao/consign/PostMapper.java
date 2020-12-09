package com.jxywkj.application.pet.dao.consign;

import com.jxywkj.application.pet.model.consign.Post;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.right.GroupRight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName PostMapper
 * @Description: 岗位
 * @Author Aze
 * @Date 2019/7/17 17:20
 * @Version 1.0
 **/
@Mapper
@Component
public interface PostMapper {

    List<Post> listPost(@Param("stationNo") int stationNo);

    void insterPost(@Param("post") Post post);

    void updatePost(@Param("postName") String postName,@Param("postNo") String postNo, @Param("stationNo")int stationNo);

    void deletePost(@Param("postNo")String postNo, Station station);

    void insertGroupRight(@Param("groupRight") GroupRight groupRight);
}
