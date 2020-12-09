package com.jxywkj.application.pet.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 *
 * </p>
 *
 * @author LiuXiangLin
 * @version 1.0
 * @className IndexController
 * @date 2020/1/9 17:12
 **/
@RequestMapping("/")
@Controller
public class IndexController {

    @RequestMapping
    public ModelAndView toIndex() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/index");

        return mv;
    }
}
