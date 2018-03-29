package com.pengyd.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pengyd.bean.Contract;
import com.pengyd.dao.ContractMapper;
import com.pengyd.service.ContractService;
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
public class ContractServiceImpl implements ContractService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Resource
    private ContractMapper contractMapper;

    /**
     * 将 Contract 插入到数据库中
     */
    public ReturnData insert(Contract contract) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            contractMapper.insert(contract);
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
     * 将 Contract 中的参数 删除数据库中的数据
     */
    public ReturnData delete(Contract contract) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            contractMapper.delete(contract);
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
     * 将 Contract 中的参数 批量删除数据库中的数据
     */
    public ReturnData deleteBatch(String[] ids) {
        ReturnData rd = new ReturnData();
        try {
            contractMapper.deleteBatch(ids);
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
     * 依据 Contract 中的主键修改数据库中的数据
     */
    public ReturnData update(Contract contract) {
        // TODO Auto-generated method stub
        ReturnData rd = new ReturnData();
        try {
            contractMapper.update(contract);
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
     * 执行 Contract 的分页查询
     */
    public JqGridJsonBean select(String page, String rows, String order_by, Contract contract) {
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

            //查询Contract总数据量
            int count = contractMapper.selectCount(contract);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);
            List<Contract> data = contractMapper.selectData(contract, _rows, (_page - 1) * _rows, order_by);
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
     * 执行 Contract 的分页查询 - 关联查询
     */
    public JqGridJsonBean selectRelationData(String page, String rows, String order_by, Contract contract) {
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

            //查询Contract总数据量
            int count = contractMapper.selectRelationCount(contract);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);
            List<Map<String, Object>> data = contractMapper.selectRelationData(contract, _rows, (_page - 1) * _rows,
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
    public JqGridJsonBean selectRelationDataByEmpRealname(String page, String rows, String order_by, Contract contract,
            String empRealname) {
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

            //查询Contract总数据量
            int count = contractMapper.selectRelationCountByEmpRealname(contract, empRealname);
            //根据查询条件查询总页数
            int pages = (count % Integer.parseInt(rows)) == 0 ? (count / _rows) : ((count / _rows) + 1);
            List<Map<String, Object>> data = contractMapper.selectRelationDataByEmpRealname(contract, _rows,
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
     * 执行 Contract 的查询不分页
     */
    public ReturnData selectByParam(String order_by, Contract contract) {
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

            List<Contract> data = contractMapper.selectByParam(contract, order_by);
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