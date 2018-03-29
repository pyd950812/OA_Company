package com.pengyd.service.impl;

import com.google.common.collect.Maps;
import com.pengyd.bean.EmployeeEvection;
import com.pengyd.dao.EmployeeEvectionMapper;
import com.pengyd.service.EmployeeEvectionService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @Author pengyd
 * @Date 2018/3/22 17:00
 * @function:
 */
@Service
public class EmployeeEvectionServiceImpl implements EmployeeEvectionService{
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private EmployeeEvectionMapper employeeEvectionMapper;


    public ReturnData insert(EmployeeEvection employeeEvection) {
        ReturnData returnData = new ReturnData();
        try {
            employeeEvectionMapper.insert(employeeEvection);
            returnData.setCode("OK");
            returnData.setMsg("插入成功！");
        }catch (Exception e){
            logger.error(e.getMessage());
            returnData.setCode("ERROR");
            returnData.setMsg("数据插入失败!");
        }
        return returnData;
    }

    @Override
    public JqGridJsonBean adminSelect(String page, String rows, String order_by, EmployeeEvection employeeEvection) {
        JqGridJsonBean jgjb = new JqGridJsonBean();
        try{
            int _rows = Integer.parseInt(rows);
            int _page = Integer.parseInt(page);
            if(order_by != null && !order_by.equals("")){

            }else {
                order_by = "id";
            }
            //查询employeeEvection总数量
            int count = employeeEvectionMapper.selectCount(employeeEvection);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);

            List<EmployeeEvection> data = employeeEvectionMapper.selectData(employeeEvection, _rows, (_page - 1) * _rows, order_by);
            jgjb.setPage(_page);// 第几页
            jgjb.setRecords(count);// 总数据量
            jgjb.setTotal(pages);// 总页数
            jgjb.setRoot(data);// 查询数据

        }catch (Exception e) {
            logger.error(e.getMessage());
        }

        return jgjb;
    }

    @Override
    public ReturnData selectByParam(EmployeeEvection employeeEvection, String order_by) {
        ReturnData rd = new ReturnData();
        try {
            if(order_by != null && !order_by.equals("")){

            }else {
                order_by = "id";
            }
            List<EmployeeEvection> data = employeeEvectionMapper.selectByParam(employeeEvection, order_by);
            Map<String ,Object> dataMap = Maps.newHashMap();
            dataMap.put("data",data);
            rd.setCode("OK");
            rd.setData(dataMap);
        }catch (Exception e){
            logger.error(e.getMessage());
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    @Override
    public ReturnData update(EmployeeEvection employeeEvection) {
        ReturnData rd = new ReturnData();
        try{
            employeeEvectionMapper.update(employeeEvection);
            rd.setCode("OK");
            rd.setMsg("修改数据成功！");
        }catch (Exception e){
            logger.error(e.getMessage());
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    @Override
    public ReturnData deleteBatch(String[] ids) {
        ReturnData rd = new ReturnData();
        try {
            employeeEvectionMapper.deleteBatch(ids);
            rd.setCode("OK");
            rd.setMsg("删除数据成功！");
        }catch (Exception e){
            logger.error(e.getMessage());
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    @Override
    public ReturnData selectByRealName(String realName) {
        ReturnData rd = new ReturnData();
        try{
            List<EmployeeEvection> data = employeeEvectionMapper.selectByRealName(realName);
            Map<String ,Object> dataMap = Maps.newHashMap();
            dataMap.put("data",data);
            rd.setCode("OK");
            rd.setData(dataMap);
        }catch (Exception e){
            logger.error(e.getMessage());
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }


}
