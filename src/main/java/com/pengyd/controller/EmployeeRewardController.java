package com.pengyd.controller;

import com.google.gson.Gson;
import com.mysql.jdbc.V1toV2StatementInterceptorAdapter;
import com.pengyd.bean.Employee;
import com.pengyd.bean.EmployeeEvection;
import com.pengyd.bean.EmployeeReward;
import com.pengyd.service.EmployeeRewardService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.apache.ecs.wml.Em;
import org.apache.ecs.wml.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author pengyd
 * @Date 2018/3/30 18:47
 * @function: 奖罚
 */
@Controller
@RequestMapping("employeeReward")
public class EmployeeRewardController {

    @Autowired
    private EmployeeRewardService employeeRewardService;
    /**
     * 以admin身份 登入
     */
    @RequestMapping(value = "admin")
    public String admin(){
        return "rewardandpunish/admin";
    }

    /**
     * 以other身份 登入
     */
    @RequestMapping(value = "other")
    public String other(){
        return "rewardandpunish/other";
    }

    /**
     * 以admin身份 添加
     */
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String add(HttpServletRequest request, Model model){
        String function = request.getParameter("function");
        model.addAttribute("function",function);
        return "rewardandpunish/add";
    }


    /**
     *  以admin身份查看所有的奖罚信息
     */
    @RequestMapping(value = "/adminSelect", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean adminSelect(String GridParam, HttpServletRequest request){
        EmployeeReward employeeReward = new Gson().fromJson(GridParam, EmployeeReward.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        JqGridJsonBean jqGridJsonBean = employeeRewardService.adminSelect(page, rows, order_by, employeeReward);
        List<EmployeeReward> root =(List<EmployeeReward>) jqGridJsonBean.getRoot();
        for(EmployeeReward e : root){
            System.out.println(e.toString());
        }
        return employeeRewardService.adminSelect(page, rows, order_by, employeeReward);
    }



    /**
     *  对 employeeReward 数据进行插入操作
     */
    @RequestMapping(value = "insert",method = RequestMethod.POST)
    @ResponseBody
    public ReturnData insert(@RequestBody EmployeeReward employeeReward){
        System.out.println(employeeReward.toString());
        return employeeRewardService.insert(employeeReward);
    }


    /**
     *   admin修改出差信息 页面上显示修改之前的用户出差信息
     */
    @RequestMapping(value = "edit",method = RequestMethod.GET)
    public String edit(HttpServletRequest request, Model model){
        String id = request.getParameter("id");

        EmployeeReward employeeReward = new EmployeeReward();
        employeeReward.setId(Integer.parseInt(id));

        ReturnData returnData = employeeRewardService.selectByParam(employeeReward, null);
        if(returnData.getCode().equals("OK")){
            List<EmployeeReward> data =(List<EmployeeReward>) returnData.getData().get("data");
            model.addAttribute("olddata",data.get(0));
        }
        return "rewardandpunish/add";
    }

    /**
     *  对 employeeReward 数据进行修改操作
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public ReturnData update(@RequestBody EmployeeReward employeeReward){
        System.out.println(employeeReward.toString());
        return employeeRewardService.update(employeeReward);
    }

    /**
     *  批量删除奖罚信息
     */
    @RequestMapping(value = "deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    public ReturnData delete(HttpServletRequest request){
        String ids = request.getParameter("ids");
//        System.out.println(ids+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        ReturnData rd = new ReturnData();
        if(ids == null || ids.equals("")){
            rd.setCode("ERROR");
            rd.setMsg("ids为空");
        }else {
            rd = employeeRewardService.deleteBatch(ids.split(","));
        }
        return rd;
    }

    /**
     *  按 empName 模糊查询 分页
     */
    @RequestMapping(value = "selectEmpNameData",method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectEmpNameData(String GridParam,Model model, HttpServletRequest request){
        EmployeeReward employeeReward = new Gson().fromJson(GridParam, EmployeeReward.class);
        String empName = employeeReward.getEmpName();
        if(!"".equals(empName)){
            empName = "%"+empName+"%";
        }
        employeeReward.setEmpName(empName);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        return employeeRewardService.adminSelect(page,rows,order_by,employeeReward);
    }


    /**
     *  展示个人查看自己所有的出差记录
     */
    @RequestMapping(value = "otherSelect",method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean otherSelect(String GridParam, Model model, HttpServletRequest request){
        EmployeeReward employeeReward = new Gson().fromJson(GridParam, EmployeeReward.class);
        Employee employee =(Employee) request.getSession().getAttribute("employee");
//        System.out.println(employee.toString()+"!!!!!!!!!!!!!!");
        employeeReward.setEmpName(employee.getRealname());

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        JqGridJsonBean jqGridJsonBean = employeeRewardService.adminSelect(page, rows, order_by, employeeReward);
        List<EmployeeReward> root =(List<EmployeeReward>) jqGridJsonBean.getRoot();
        for(EmployeeReward e : root){
            System.out.println(e.toString());
        }
        return employeeRewardService.adminSelect(page,rows,order_by,employeeReward);
    }




}
