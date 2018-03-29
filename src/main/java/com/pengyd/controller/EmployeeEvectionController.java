package com.pengyd.controller;

import com.google.gson.Gson;
import com.pengyd.bean.Employee;
import com.pengyd.bean.EmployeeEvection;
import com.pengyd.service.EmployeeEvectionService;
import com.pengyd.service.EmployeeService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


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
    @Autowired
    private EmployeeService employeeService;


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
     *    以admin的身份 跳转到 添加 员工出差信息 的页面
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(HttpServletRequest request, Model model){
        String function = request.getParameter("function");
        model.addAttribute("function",function);
        return "evection/add";
    }

    /**
     * 对 employeeEvection 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData insert(@RequestBody EmployeeEvection employeeEvection) {
        return employeeEvectionService.insert(employeeEvection);
    }

    /**
     *   admin查询所有的出差记录
     */
    @RequestMapping(value = "/adminSelect", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean adminSelect(String GridParam, Model model, HttpServletRequest request){
        EmployeeEvection employeeEvection = new Gson().fromJson(GridParam, EmployeeEvection.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        JqGridJsonBean jqGridJsonBean = employeeEvectionService.adminSelect(page, rows, order_by, employeeEvection);
        List<EmployeeEvection> root =(List<EmployeeEvection>) jqGridJsonBean.getRoot();
        for(EmployeeEvection e : root){
            System.out.println(e.toString());
        }
        return employeeEvectionService.adminSelect(page, rows, order_by, employeeEvection);
    }

    /**
     *   admin修改出差信息 页面上显示修改之前的用户出差信息
     */
    @RequestMapping(value = "edit",method = RequestMethod.GET)
    public String edit(HttpServletRequest request,Model model){
        String id = request.getParameter("id");

        EmployeeEvection employeeEvection = new EmployeeEvection();
        employeeEvection.setId(Integer.valueOf(Integer.parseInt(id)));

        ReturnData rd = employeeEvectionService.selectByParam(employeeEvection,null);
        if (rd.getCode().equals("OK")) {
            List<EmployeeEvection> data = (List<EmployeeEvection>) rd.getData().get("data");

            model.addAttribute("olddata", data.get(0));
        }
        return "evection/add";
    }

    /**
     *  对EmployeeEvection数据进行修改
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public ReturnData update(@RequestBody EmployeeEvection employeeEvection){
        System.out.println(employeeEvection);
        ReturnData rd = employeeEvectionService.update(employeeEvection);
        return rd;
    }


    /**
     *  根据empId查看员工是否存在
     */
    @RequestMapping(value = "selectByEmpId",method = RequestMethod.POST)
    @ResponseBody
    public Employee selectByEmpId(String empId,Model model){
        Employee employee = employeeService.selectByEmpId(empId);
        System.out.println(employee);
        if(employee == null){
            Employee employee1 = new Employee();
            employee1.setRealname("ERROR");
            return employee1;
        }
            return employee;
    }

    /**
     *  批量删除出差信息
     */
    @RequestMapping(value = "deleteBatch",method = RequestMethod.POST)
    @ResponseBody
    public ReturnData delete(HttpServletRequest request){
        String ids = request.getParameter("ids");
        ReturnData rd = new ReturnData();
        if(ids == null || ids.equals("")){
            rd.setCode("ERROR");
            rd.setMsg("ids为空");
        }else {
            rd = employeeEvectionService.deleteBatch(ids.split(","));
        }
        return rd;
    }

    /**
     *  展示个人查看自己所有的出差记录
     */
    @RequestMapping(value = "otherSelect",method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean otherSelect(String GridParam, Model model, HttpServletRequest request){
        EmployeeEvection employeeEvection = new Gson().fromJson(GridParam, EmployeeEvection.class);
        Employee employee =(Employee) request.getSession().getAttribute("employee");
        employeeEvection.setEmpName(employee.getRealname());

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        JqGridJsonBean jqGridJsonBean = employeeEvectionService.adminSelect(page, rows, order_by, employeeEvection);
        List<EmployeeEvection> root =(List<EmployeeEvection>) jqGridJsonBean.getRoot();
        for(EmployeeEvection e : root){
            System.out.println(e.toString());
        }
        return employeeEvectionService.adminSelect(page,rows,order_by,employeeEvection);
    }



    /**
     * 按empName条件查询 分页
     */
    @RequestMapping(value = "/selectEmpNameData", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectEmpNameData(String GridParam, Model model, HttpServletRequest request) {
        EmployeeEvection employeeEvection = new Gson().fromJson(GridParam, EmployeeEvection.class);//json 转对象
        String empName = employeeEvection.getEmpName();
        if(!"".equals(empName)){
            empName = "%"+empName+"%";
        }
        employeeEvection.setEmpName(empName);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return employeeEvectionService.adminSelect(page, rows, order_by, employeeEvection);
    }














}
