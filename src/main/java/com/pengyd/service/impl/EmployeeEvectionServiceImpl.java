package com.pengyd.service.impl;

import com.pengyd.bean.EmployeeEvection;
import com.pengyd.dao.EmployeeEvectionMapper;
import com.pengyd.service.EmployeeEvectionService;
import com.pengyd.util.ReturnData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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


    @Override
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






}
