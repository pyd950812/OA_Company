package com.pengyd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author pengyd
 * @Date 2018/3/30 18:47
 * @function:
 */
@Controller
@RequestMapping("reward")
public class EmployeeRewardController {

    @RequestMapping(value = "admin")
    public String admin(){
        return "rewardandpunish/admin";
    }
}
