package com.pengyd.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pengyd.bean.DeptPerm;
import com.pengyd.bean.Employee;
import com.pengyd.service.DeptPermService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import com.google.gson.Gson;

/**
 * @Author pengyd
 * @Date 2018/3/22 17:08
 * @function:
 */
@Controller
@RequestMapping(value = "/dept_perm")
public class DeptPermController {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private DeptPermService deptPermService;

    /**
     * 数据展示页面
     * @return
     */
    @RequiresPermissions(value = "dept_perm_show")
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(Model model, HttpServletRequest request) {
        return "dept_perm/show";
    }

    /**
     * 数据新增页面
     * @return
     */
    @RequiresPermissions(value = "dept_perm_add")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model, HttpServletRequest request) {
        return "dept_perm/add";
    }

    /**
     * 数据修改页面
     * @return
     */
    @RequiresPermissions(value = "dept_perm_edit")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(Model model, HttpServletRequest request) {
        String id = request.getParameter("id");

        DeptPerm deptPerm = new DeptPerm();
        deptPerm.setId(Integer.valueOf(Integer.parseInt(id)));

        ReturnData rd = deptPermService.selectByParam(null, deptPerm);
        if (rd.getCode().equals("OK")) {
            List<DeptPerm> data = (List<DeptPerm>) rd.getData().get("data");

            model.addAttribute("olddata", JSON.toJSONString(data.get(0)));
        }
        return "dept_perm/edit";
    }

    /**
     * 对 dept_perm 的数据插入操作
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData insert(@RequestBody DeptPerm deptPerm, Model model, HttpServletRequest request) {
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        return deptPermService.insert(deptPerm);//执行插入 DeptPerm 操作
    }

    /**
     * 对 dept_perm 的数据删除操作
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData delete(@RequestBody DeptPerm deptPerm, Model model, HttpServletRequest request) {
        return deptPermService.delete(deptPerm);//执行删除 DeptPerm  操作
    }

    /**
     * 对 dept_perm 的数据批量删除操作
     * @param request 请求数据
     */
    @RequestMapping({ "/deleteBatch" })
    @ResponseBody
    public ReturnData deleteBatch(HttpServletRequest request) {
        ReturnData rd = new ReturnData();
        String ids = request.getParameter("ids");
        if ((ids == null) || ("".equals(ids))) {
            rd.setCode("ERROR");
            rd.setMsg("ids为空");
        }
        else {
            rd = deptPermService.deleteBatch(ids.split(","));
        }
        return rd;
    }

    /**
     * 对 dept_perm 的数据修改操作
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData update(@RequestBody DeptPerm deptPerm, Model model, HttpServletRequest request) {
        Employee currentEmp = ((Employee) request.getSession().getAttribute("current_emp"));

        return deptPermService.update(deptPerm);//执行 DeptPerm  操作
    }

    /**
     * 对 dept_perm 的数据分页查询操作
     */
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean select(String GridParam, Model model, HttpServletRequest request) {
        DeptPerm deptPerm = new Gson().fromJson(GridParam, DeptPerm.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return deptPermService.select(page, rows, order_by, deptPerm);
    }

    /**
     * 对 dept_perm 的数据分页查询操作 - 关联查询
     */
    @RequestMapping(value = "/selectRelationData", method = RequestMethod.POST)
    @ResponseBody
    public JqGridJsonBean selectRelationData(String GridParam, Model model, HttpServletRequest request) {
        DeptPerm deptPerm = new Gson().fromJson(GridParam, DeptPerm.class);//json 转对象

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序

        //分页查询
        return deptPermService.selectRelationData(page, rows, order_by, deptPerm);
    }

    /**
     * 对 dept_perm 的数据查询操作不分页
     */
    @RequestMapping(value = "/selectByParam", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ReturnData selectByParam(@RequestBody DeptPerm deptPerm, Model model, HttpServletRequest request) {
        String order_by = request.getParameter("order_by");//排序

        return deptPermService.selectByParam(order_by, deptPerm);
    }

    /**
     * 对 dept_perm 的数据导出操作
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletRequest request, HttpServletResponse response) {
        //1、使用JSONObject
        String json = request.getParameter("json");
        DeptPerm deptPerm = new Gson().fromJson(json, DeptPerm.class);

        String page = request.getParameter("page");//第几页
        String rows = request.getParameter("rows");//一页有几行
        String order_by = request.getParameter("order_by");//排序
        //分页查询
        JqGridJsonBean rd = deptPermService.select(page, rows, order_by, deptPerm);

        //创建HSSFWorkbook对象(excel的文档对象)  
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）  
        HSSFSheet sheet = wb.createSheet("deptPerm");
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个  
        HSSFRow row1 = sheet.createRow(0);

        //创建单元格并设置单元格内容  
        row1.createCell(1 - 1).setCellValue("主键");
        row1.createCell(2 - 1).setCellValue("部门ID");
        row1.createCell(3 - 1).setCellValue("权限ID");
        //在sheet里创建第三行  
        @SuppressWarnings("unchecked")
        List<DeptPerm> maps = (List<DeptPerm>) rd.getRoot();
        for (int i = 0; i < maps.size(); i++) {
            DeptPerm map = maps.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(1 - 1).setCellValue(map.getId() + "");
            row.createCell(2 - 1).setCellValue(map.getDeptId() + "");
            row.createCell(3 - 1).setCellValue(map.getPermId() + "");
        }

        //输出Excel文件  
        try {
            ServletOutputStream output = response.getOutputStream();
            String fileName = new String(("导出deptPerm").getBytes(), "ISO8859_1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");
            response.setContentType("application/binary;charset=utf-8");
            wb.write(output);
            output.flush();
            output.close();

        }
        catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * 对 dept_perm 的数据导入操作
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public ReturnData _import(@RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletResponse response) {
        ReturnData rd = new ReturnData();
        String filename = file.getOriginalFilename();
        if (filename == null || "".equals(filename)) {
            return null;
        }
        try {
            InputStream XSSFinput = file.getInputStream();
            InputStream HSSFinput = file.getInputStream();
            Workbook workBook = null;
            try {
                workBook = new XSSFWorkbook(XSSFinput);
            }
            catch (Exception ex) {
                workBook = new HSSFWorkbook(HSSFinput);
            }

            Sheet sheet = workBook.getSheetAt(0);
            if (sheet != null) {
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);
                    DeptPerm deptPerm = new DeptPerm();
                    //System.out.println(row.getCell(0));
                    //此处自己添字段例如 myTable.set...(row.getCell(0))

                    //deptPermService.insert(deptPerm);  
                }

            }
        }
        catch (Exception e) {
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
            //e.printStackTrace();  
        }

        rd.setCode("OK");
        rd.setMsg("数据导入成功");
        return rd;
    }
}