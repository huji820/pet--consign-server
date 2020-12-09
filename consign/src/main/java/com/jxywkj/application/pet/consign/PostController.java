package com.jxywkj.application.pet.consign;

import com.jxywkj.application.pet.common.utils.JsonResult;
import com.jxywkj.application.pet.model.consign.Post;
import com.jxywkj.application.pet.model.consign.Staff;
import com.jxywkj.application.pet.model.consign.right.GroupRight;
import com.jxywkj.application.pet.service.facade.common.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName PostController
 * @Description: 岗位Controller
 * @Author Aze
 * @Date 2019/7/17 17:05
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consign/post")
public class PostController {

    @Autowired
    PostService postService;
    /**
     * @Author Aze
     * @Description: 获取岗位列表
     * @Date:
     * @Param
     * @return
     */
    @GetMapping("")
    public JsonResult listPost(@SessionAttribute("staff")Staff staff){
        return  JsonResult.success(postService.listPost(staff.getStation().getStationNo()));
    }

    /**
     * @Author Aze
     * @Description: 新增岗位
     * @Date:
     * @Param
     * @return
     */

    @PostMapping("")
    public  JsonResult insterPost(@RequestParam("postName")String postName,
                                  @RequestParam("partitionSelected") List<String> partitionSelected,
                                  @SessionAttribute("staff")Staff staff){
        Post post = new Post();
        post.setPostName(postName);
        post.setStation(staff.getStation());

        String ids = partitionSelected.stream().filter(str -> !str.isEmpty()).collect(Collectors.joining(","));

        GroupRight groupRight = new GroupRight();
        groupRight.setTgrName(postName);
        groupRight.setTrIds(ids);
//        postService.insertGroupRight(groupRight);
        postService.insterPost(post);
        return  JsonResult.success("新增岗位成功");
    }

    /**
     * @Author Aze
     * @Description: 修改岗位
     * @Date:
     * @Param
     * @return
     */
    @PutMapping()
    public  void updatePost(@RequestParam("postName") String postName,@RequestParam("postNo")  String postNo,
                            @SessionAttribute(value = "staff")Staff staff){
        postService.updatePost(postName,postNo,staff.getStation().getStationNo());
    }

    @PutMapping("/deletePost")
    public  void deletePost(@RequestParam("postNo")  String postNo,
                            @SessionAttribute(value = "staff")Staff staff){
        postService.deletePost(postNo,staff.getStation());
    }

}
