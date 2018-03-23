package com.pengyd.controller;

import com.pengyd.bean.EmployeeEvection;
import com.pengyd.service.EmployeeEvectionService;
import com.pengyd.util.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function:
 */
@Controller
@RequestMapping("/employeeEvection")
public class EmployeeEvectionController {

    @Autowired
    private EmployeeEvectionService employeeEvectionService;


    //跳转到 员工出差管理
    @RequestMapping("/evection")
    public String evection(){
        return "employee/evection";
    }

    /**
     * 对 employeeEvection 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData insert(@RequestParam EmployeeEvection employeeEvection) {
        return employeeEvectionService.insert(employeeEvection);
    }





}
