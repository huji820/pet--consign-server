package com.jxywkj.application.pet.service.facade.common;

import com.jxywkj.application.pet.model.consign.Post;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.right.GroupRight;
import javafx.geometry.Pos;

import java.util.List;

/**
 * @ClassName PostService
 * @Description: 岗位
 * @Author Aze
 * @Date 2019/7/17 17:08
 * @Version 1.0
 **/
public interface PostService {

    /*
     * @Author Aze
     * @Description: 获取所有岗位
     * @Date:
     * @Param
     * @return
     */

    List<Post> listPost(int stationNo);

    /*
     * @Author Aze
     * @Description: 新增岗位
     * @Date:
     * @Param
     * @return
     */

    void insterPost(Post post);

    /*
     * @Author Aze
     * @Description: 删除岗位
     * @Date:
     * @Param
     * @return
     */

    void deletePost(String postNo, Station station);

    /*
     * @Author Aze
     * @Description: 修改岗位
     * @Date:
     * @Param
     * @return
     */
    void  updatePost(String postName,String postNo,int stationNo);

    /**

     * @description 新增岗位
     * @return
     * @author lsy
     * @date 10:36 2020/1/15
     **/
    void insertGroupRight(GroupRight groupRight);
}
