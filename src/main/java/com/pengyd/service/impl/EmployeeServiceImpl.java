package com.pengyd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pengyd.bean.Employee;
import com.pengyd.dao.EmployeeMapper;
import com.pengyd.service.EmployeeService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * @Author pengyd
 * @Date 2018/3/22 17:00
 * @function:
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private EmployeeMapper employeeMapper;

    /**
     * 将 Employee 插入到数据库中
     */
    public ReturnData insert(Employee employee) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            employeeMapper.insert(employee);
            rd.setCode("OK");
            rd.setMsg("数据插入成功 ");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //执行插入操作异常,返回错误信息
            rd.setCode("ERROR");
            rd.setMsg("数据插入失败，请重新填写！");
        }

        return rd;
    }

    /**
     * 将 Employee 中的参数 删除数据库中的数据
     */
    public ReturnData delete(Employee employee) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            employeeMapper.delete(employee);
            rd.setCode("OK");
            rd.setMsg("数据删除成功 ");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //执行插入操作异常,返回错误信息
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    /**
     * 将 Employee 中的参数 批量删除数据库中的数据
     */
    public ReturnData deleteBatch(String[] ids) {
        ReturnData rd = new ReturnData();
        try {
            employeeMapper.deleteBatch(ids);
            rd.setCode("OK");
            rd.setMsg("数据删除成功 ");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    /**
     * 依据 Employee 中的主键修改数据库中的数据
     */
    public ReturnData update(Employee employee) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            Employee employee1 = employeeMapper.selectByLoginName(employee.getLoginname());
            employee.setId(employee1.getId());
            employeeMapper.update(employee);
            rd.setCode("OK");
            rd.setMsg("数据修改成功");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //执行插入操作异常,返回错误信息
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    /**
     * 执行 Employee 的分页查询
     */
    public JqGridJsonBean select(String page, String rows, String order_by, Employee employee) {
        // TODO Auto-generated method stub
        JqGridJsonBean jgjb = new JqGridJsonBean();
        try {
            int _page = Integer.parseInt(page);
            int _rows = Integer.parseInt(rows);
            //没有order_by 默认主键排序
            if (order_by != null && !"".equals(order_by)) {
                //不变
            }
            else {
                order_by = "id";
            }

            //查询Employee总数据量
            int count = employeeMapper.selectCount(employee);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);

            List<Employee> data = employeeMapper.selectData(employee, _rows, (_page - 1) * _rows, order_by);
            jgjb.setPage(_page);// 第几页
            jgjb.setRecords(count);// 总数据量
            jgjb.setTotal(pages);// 总页数
            jgjb.setRoot(data);// 查询数据信息
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //执行插入操作异常,返回错误信息
        }
        return jgjb;
    }

    /**
     * 执行 Employee 的查询不分页
     */
    public ReturnData selectByParam(String order_by, Employee employee) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            //没有order_by 默认主键排序
            if (order_by != null && !"".equals(order_by)) {
                //不变
            }
            else {
                order_by = "id";
            }

            List<Employee> data = employeeMapper.selectByParam(employee, order_by);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("data", data);
            rd.setCode("OK");
            rd.setData(dataMap);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //执行插入操作异常,返回错误信息
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    @Override
    public ReturnData ajaxSelectMaxEmpCode() {
        ReturnData rd = new ReturnData();
        try {
            String data = employeeMapper.ajaxSelectMaxEmpCode();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("data", data);
            rd.setCode("OK");
            rd.setData(dataMap);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());

            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    @Override
    public JqGridJsonBean selectRelationData(String page, String rows, String order_by, Employee employee) {
        JqGridJsonBean jgjb = new JqGridJsonBean();
        try {
            int _page = Integer.parseInt(page);
            int _rows = Integer.parseInt(rows);
            //没有order_by 默认主键排序
            if (order_by != null && !"".equals(order_by)) {
                //不变
            }
            else {
                order_by = "id";
            }

            //查询Employee总数据量
            int count = employeeMapper.selectRelationCount(employee);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);
            List<Map<String, Object>> data = employeeMapper.selectRelationData(employee, _rows, (_page - 1) * _rows,
                    order_by);
            jgjb.setPage(_page);// 第几页
            jgjb.setRecords(count);// 总数据量
            jgjb.setTotal(pages);// 总页数
            jgjb.setRoot(data);// 查询数据信息
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //执行插入操作异常,返回错误信息
        }
        return jgjb;
    }

    @Override
    public ReturnData ajaxSelectEmpByJobposId(String jobposId) {
        ReturnData rd = new ReturnData();
        try {
            List<Map<Integer, String>> data = employeeMapper.ajaxSelectEmpByJobposId(jobposId);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("data", data);
            rd.setCode("OK");
            rd.setData(dataMap);
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());

            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    @Override
    public String selectRealnameById(Integer id) {
        return employeeMapper.selectRealnameById(id);
    }

    @Override
    public Employee selectByEmpId(String empId) {
        Employee employee = employeeMapper.selectByEmpId(empId);
        return employee;
    }

    @Override
    public Employee selectByLoginName(String loginName) {
        Employee employee = employeeMapper.selectByLoginName(loginName);
        return employee;
    }

    @Override
    public ReturnData selectEmpIds() {
        ReturnData rd = new ReturnData();
        try {
            List<Integer> data = employeeMapper.selectEmpIds();
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("data", data);
            rd.setCode("OK");
            rd.setData(dataMap);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //执行插入操作异常,返回错误信息
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }
}