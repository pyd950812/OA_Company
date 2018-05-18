package com.pengyd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pengyd.bean.Permission;
import com.pengyd.bean.XtreeData;
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

    @Override
    public List<XtreeData> selXtreeData() {
        List<XtreeData> list1 = new ArrayList<>();

        //获取到所有的权限permission
        Permission permission = new Permission();
        List<Permission> permList = permissionMapper.selectByParam(permission, "id");

        for (int i = 0; i < permList.size(); i++) {
            Permission permission1 = permList.get(i);
            //首先找到parent_id=0的permission 即人事信息管理以及工作管理
            if (permission1.getParentId() == 0) {//父菜单
                XtreeData x1 = new XtreeData();
                int permId1 = permission1.getId();
                x1.setValue(permId1 + "");
                x1.setTitle(permission1.getName());

                List<XtreeData> list2 = new ArrayList<>();
                //然后找到人事信息管理以及工作管理 以下的
                for (int j = 0; j < permList.size(); j++) {
                    Permission permission2 = permList.get(j);
                    if (permission2.getParentId() == permId1) {
                        XtreeData x2 = new XtreeData();
                        int permId2 = permission2.getId();
                        x2.setValue(permId2 + "");
                        x2.setTitle(permission2.getName());

                        List<XtreeData> list3 = new ArrayList<>();
                        for (int k = 0; k < permList.size(); k++) {
                            Permission permission3 = permList.get(k);
                            if (permission3.getParentId() == permId2) {
                                XtreeData x3 = new XtreeData();
                                int permId3 = permission3.getId();
                                x3.setValue(permId3 + "");
                                x3.setTitle(permission3.getName());

                                // 是否拥有权限
                                //x3.setChecked(false);

                                // 使数据data不为null
                                List<XtreeData> l = new ArrayList<>();
                                x3.setData(l);
                                list3.add(x3);
                            }
                        }

                        x2.setData(list3);
                        // 是否拥有权限
                        //x2.setChecked(false);

                        list2.add(x2);
                    }
                }

                x1.setData(list2);
                // 是否拥有权限
                //x1.setChecked(false);

                list1.add(x1);
            }
        }

        // 拥有没有子节点的节点，设置选中
        for (XtreeData xd : list1) {
            if (xd.getData() == null || xd.getData().size() == 0) {
                for (Permission perm : permList) {
                    if (perm.getId() == Long.parseLong(xd.getValue())) {
                        xd.setChecked(true);
                    }
                }
            }
        }

//        System.out.println(list1);

        //默认拥有首页菜单权限
        /*list1.get(0).setDisabled(true);
        list1.get(0).setChecked(true);*/

        return list1;
    }

    @Override
    public List<XtreeData> selXtreeData(List<Integer> permValue) {
        List<XtreeData> list1 = new ArrayList<>();

        Permission permission = new Permission();
        List<Permission> permList = permissionMapper.selectByParam(permission, "id");

        for (int i = 0; i < permList.size(); i++) {
            Permission permission1 = permList.get(i);
            if (permission1.getParentId() == 0) {//父菜单
                XtreeData x1 = new XtreeData();
                int permId1 = permission1.getId();
                x1.setValue(permId1 + "");
                x1.setTitle(permission1.getName());

                if (permValue.contains(permId1)) {
                    x1.setChecked(true);
                }

                List<XtreeData> list2 = new ArrayList<>();

                for (int j = 0; j < permList.size(); j++) {
                    Permission permission2 = permList.get(j);
                    if (permission2.getParentId() == permId1) {
                        XtreeData x2 = new XtreeData();
                        int permId2 = permission2.getId();
                        x2.setValue(permId2 + "");
                        x2.setTitle(permission2.getName());

                        if (permValue.contains(permId2)) {
                            x2.setChecked(true);
                        }

                        List<XtreeData> list3 = new ArrayList<>();
                        for (int k = 0; k < permList.size(); k++) {
                            Permission permission3 = permList.get(k);
                            if (permission3.getParentId() == permId2) {
                                XtreeData x3 = new XtreeData();
                                int permId3 = permission3.getId();
                                x3.setValue(permId3 + "");
                                x3.setTitle(permission3.getName());

                                //是否拥有权限
                                //x3.setChecked(false);

                                if (permValue.contains(permId3)) {
                                    x3.setChecked(true);
                                }

                                // 使数据data不为null
                                List<XtreeData> l = new ArrayList<>();
                                x3.setData(l);
                                list3.add(x3);
                            }
                        }

                        x2.setData(list3);

                        list2.add(x2);
                    }
                }

                x1.setData(list2);

                list1.add(x1);
            }
        }

        // 拥有没有子节点的节点，设置选中
        for (XtreeData xd : list1) {
            if (xd.getData() == null || xd.getData().size() == 0) {
                for (Permission perm : permList) {
                    if (perm.getId() == Long.parseLong(xd.getValue())) {
                        xd.setChecked(true);
                    }
                }
            }
        }

        return list1;
    }
}