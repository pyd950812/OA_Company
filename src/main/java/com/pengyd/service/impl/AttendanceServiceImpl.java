package com.pengyd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pengyd.bean.Attendance;
import com.pengyd.dao.AttendanceMapper;
import com.pengyd.service.AttendanceService;
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
public class AttendanceServiceImpl implements AttendanceService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private AttendanceMapper attendanceMapper;

    /**
     * 将 Attendance 插入到数据库中
     */
    public ReturnData insert(Attendance attendance) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            attendanceMapper.insert(attendance);
            rd.setCode("OK");
            rd.setMsg("数据插入成功 ");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //操作异常,返回错误信息
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }

        return rd;
    }

    /**
     * 将 Attendance 中的参数 删除数据库中的数据
     */
    public ReturnData delete(Attendance attendance) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            attendanceMapper.delete(attendance);
            rd.setCode("OK");
            rd.setMsg("数据删除成功 ");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //操作异常,返回错误信息
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    /**
     * 将 Attendance 中的参数 批量删除数据库中的数据
     */
    public ReturnData deleteBatch(String[] ids) {
        ReturnData rd = new ReturnData();
        try {
            attendanceMapper.deleteBatch(ids);
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
     * 依据 Attendance 中的主键修改数据库中的数据
     */
    public ReturnData update(Attendance attendance) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            attendanceMapper.update(attendance);
            rd.setCode("OK");
            rd.setMsg("数据删除成功 ");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //操作异常,返回错误信息
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

    /**
     * 执行 Attendance 的分页查询
     */
    public JqGridJsonBean select(String page, String rows, String order_by, Attendance attendance) {
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

            //查询Attendance总数据量
            int count = attendanceMapper.selectCount(attendance);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);
            List<Attendance> data = attendanceMapper.selectData(attendance, _rows, (_page - 1) * _rows, order_by);
            jgjb.setPage(_page);// 第几页
            jgjb.setRecords(count);// 总数据量
            jgjb.setTotal(pages);// 总页数
            jgjb.setRoot(data);// 查询数据信息
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //操作异常,返回错误信息
        }
        return jgjb;
    }

    /**
     * 执行 Attendance 的分页查询 - 关联查询
     */
    public JqGridJsonBean selectRelationData(String page, String rows, String order_by, Attendance attendance) {
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

            //查询Attendance总数据量
            int count = attendanceMapper.selectRelationCount(attendance);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);
            List<Map<String, Object>> data = attendanceMapper.selectRelationData(attendance, _rows, (_page - 1) * _rows,
                    order_by);
            jgjb.setPage(_page);// 第几页
            jgjb.setRecords(count);// 总数据量
            jgjb.setTotal(pages);// 总页数
            jgjb.setRoot(data);// 查询数据信息
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //操作异常,返回错误信息
        }
        return jgjb;
    }

    @Override
    public JqGridJsonBean selectRelationDataByEmpRealname(String page, String rows, String order_by,
            Attendance attendance, String empRealname) {
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

            //查询Attendance总数据量
            int count = attendanceMapper.selectRelationCountByEmpRealname(attendance, empRealname);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);
            List<Map<String, Object>> data = attendanceMapper.selectRelationDataByEmpRealname(attendance, _rows,
                    (_page - 1) * _rows, order_by, empRealname);
            jgjb.setPage(_page);// 第几页
            jgjb.setRecords(count);// 总数据量
            jgjb.setTotal(pages);// 总页数
            jgjb.setRoot(data);// 查询数据信息
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //操作异常,返回错误信息
        }
        return jgjb;
    }

    /**
     * 执行 Attendance 的查询不分页
     */
    public ReturnData selectByParam(String order_by, Attendance attendance) {
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

            List<Attendance> data = attendanceMapper.selectByParam(attendance, order_by);
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("data", data);
            rd.setCode("OK");
            rd.setData(dataMap);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            //操作异常,返回错误信息
            rd.setCode("ERROR");
            rd.setMsg(e.getMessage());
        }
        return rd;
    }

}