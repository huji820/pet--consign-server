package com.jxywkj.application.pet.service.spring.consign;

import com.jxywkj.application.pet.dao.consign.PostMapper;
import com.jxywkj.application.pet.model.consign.Post;
import com.jxywkj.application.pet.model.consign.Station;
import com.jxywkj.application.pet.model.consign.right.GroupRight;
import com.jxywkj.application.pet.service.facade.common.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PostServiceImpl
 * @Description: 岗位
 * @Author Aze
 * @Date 2019/7/17 17:09
 * @Version 1.0
 **/
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostMapper postMapper;
    /*
     * @Author Aze
     * @Description: 获取所有岗位
     * @Date:
     * @Param
     * @return
     */

    @Override
    public List<Post> listPost(int stationNo) {
        return postMapper.listPost(stationNo);
    }

    @Override
    public void insterPost(Post post) {
        postMapper.insterPost(post);
    }


    @Override
    public void deletePost(String postNo, Station station) {
        postMapper.deletePost(postNo,station);
    }

    @Override
    public void updatePost(String postName, String postNo, int stationNo) {
        postMapper.updatePost(postName,postNo,stationNo);
    }

    @Override
    public void insertGroupRight(GroupRight groupRight) {
        postMapper.insertGroupRight(groupRight);
    }
}
