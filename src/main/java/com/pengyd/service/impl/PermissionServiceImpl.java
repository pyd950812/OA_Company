package com.pengyd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pengyd.bean.Permission;
import com.pengyd.dao.PermissionMapper;
import com.pengyd.service.PermissionService;
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
public class PermissionServiceImpl implements PermissionService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private PermissionMapper permissionMapper;

    /**
     * 将 Permission 插入到数据库中
     */
    public ReturnData insert(Permission permission) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            permissionMapper.insert(permission);
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
     * 将 Permission 中的参数 删除数据库中的数据
     */
    public ReturnData delete(Permission permission) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            permissionMapper.delete(permission);
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
     * 将 Permission 中的参数 批量删除数据库中的数据
     */
    public ReturnData deleteBatch(String[] ids) {
        ReturnData rd = new ReturnData();
        try {
            permissionMapper.deleteBatch(ids);
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
     * 依据 Permission 中的主键修改数据库中的数据
     */
    public ReturnData update(Permission permission) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            permissionMapper.update(permission);
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
     * 执行 Permission 的分页查询
     */
    public JqGridJsonBean select(String page, String rows, String order_by, Permission permission) {
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

            //查询Permission总数据量
            int count = permissionMapper.selectCount(permission);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);
            List<Permission> data = permissionMapper.selectData(permission, _rows, (_page - 1) * _rows, order_by);
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
     * 执行 Permission 的分页查询 - 关联查询
     */
    public JqGridJsonBean selectRelationData(String page, String rows, String order_by, Permission permission) {
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

            //查询Permission总数据量
            int count = permissionMapper.selectRelationCount(permission);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);
            List<Map<String, Object>> data = permissionMapper.selectRelationData(permission, _rows, (_page - 1) * _rows,
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

    /**
     * 执行 Permission 的查询不分页
     */
    public ReturnData selectByParam(String order_by, Permission permission) {
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

            List<Permission> data = permissionMapper.selectByParam(permission, order_by);
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

    @Override
    public ReturnData ajaxSelectPermListByUse() {
        ReturnData rd = new ReturnData();
        try {
            List<Permission> data = permissionMapper.ajaxSelectPermListByUse();
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

    @Override
    public ReturnData selectByPermIds(String permIds) {
        ReturnData rd = new ReturnData();
        try {
            List<Permission> data = permissionMapper.selectByPermIds(permIds);
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