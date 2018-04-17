package com.pengyd.service.impl;

import com.google.common.collect.Maps;
import com.pengyd.bean.EmployeeReward;
import com.pengyd.dao.EmployeeRewardMapper;
import com.pengyd.service.EmployeeRewardService;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author pengyd
 * @Date 2018/4/4 17:22
 * @function:
 */
@Service
public class EmployeeRewardServiceImpl implements EmployeeRewardService{
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private EmployeeRewardMapper employeeRewardMapper;

    @Override
    public ReturnData insert(EmployeeReward employeeReward) {
        ReturnData rd = new ReturnData();
        try {
            employeeRewardMapper.insert(employeeReward);
            rd.setCode("OK");
            rd.setMsg("插入成功！");
        }catch (Exception e){
            logger.error(e.getMessage());
            rd.setCode("ERROR");
            rd.setMsg("数据插入失败!");
        }
        return rd;
    }

    @Override
    public JqGridJsonBean adminSelect(String page, String rows, String order_by, EmployeeReward employeeReward) {
        JqGridJsonBean jgjb = new JqGridJsonBean();
        try{
            int _rows = Integer.parseInt(rows);
            int _page = Integer.parseInt(page);
            if(order_by != null && !order_by.equals("")){

            }else {
                order_by = "id";
            }
            //查询employeeReward总数量
            int count = employeeRewardMapper.selectCount(employeeReward);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);

            List<EmployeeReward> data = employeeRewardMapper.selectData(employeeReward, _rows, (_page - 1) * _rows, order_by);
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
    public ReturnData update(EmployeeReward employeeReward) {
        ReturnData rd = new ReturnData();
        try{
            employeeRewardMapper.update(employeeReward);
            rd.setCode("OK");
            rd.setMsg("数据修改成功！");
        }catch (Exception e){
            logger.error(e.getMessage());
            rd.setCode("ERROR");
            rd.setMsg("数据修改失败！");
        }
        return rd;
    }

    @Override
    public ReturnData selectByParam(EmployeeReward employeeReward, String order_by) {
        ReturnData rd = new ReturnData();
        try{
            if(order_by != null && !order_by.equals("")){

            }else {
                order_by = "id";
            }
            List<EmployeeReward> data = employeeRewardMapper.seleteByParam(employeeReward, order_by);
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
    public ReturnData deleteBatch(String[] ids) {
        ReturnData rd = new ReturnData();
        try{
            employeeRewardMapper.deleteBatch(ids);
            rd.setCode("OK");
            rd.setMsg("数据删除成功！");

        }catch (Exception e){
            logger.error(e.getMessage());
            rd.setCode("ERROR");
            rd.setMsg("数据删除失败！");
        }
        return rd;
    }

}
