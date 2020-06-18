package com.wgy.shop.controller.consumer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 验证码控制器
 */
@Controller
public class VerificationCodeImgController {

    /**
     * 返回验证码模型和视图的集合
     * @return 返回验证码模型和视图的集合
     */
    @RequestMapping(value = "/verificationcodeimg")
    public ModelAndView verificationcodeimg(){
        ModelAndView verificationcodeimg=new ModelAndView();
        verificationcodeimg.setViewName("verificationcodeimg");
        return verificationcodeimg;
    }
}
