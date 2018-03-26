package com.pengyd.controller;

import com.google.gson.Gson;
import com.pengyd.bean.EmployeeEvection;
import com.pengyd.service.EmployeeEvectionService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


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


    /**
     *    以admin的身份 跳转到 员工出差管理
     */
    @RequestMapping("/admin")
    public String admin(){
        return "evection/admin";
    }
    /**
     *    以员工的身份 跳转到 员工出差管理
     */
    @RequestMapping("/other")
    public String other(){
        return "evection/other";
    }

    /**
     * 对 employeeEvection 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData insert(@RequestParam EmployeeEvection employeeEvection) {
        return employeeEvectionService.insert(employeeEvection);
    }

    @RequestMapping(value = "/adminSelect", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean adminSelect(String GridParam, Model model, HttpServletRequest request){
        System.out.println("!!!!!!!!!!!!!!!!!!");

        EmployeeEvection employeeEvection = new Gson().fromJson(GridParam, EmployeeEvection.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        return employeeEvectionService.adminSelect(page, rows, order_by, employeeEvection);
    }





}
