package com.pengyd.service;


import com.pengyd.bean.Contract;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;
/**
 * @Author pengyd
 * @Date 2018/3/22 17:00
 * @function:
 */
public interface ContractService {

    /**
      * 执行 Contract 插入操作
      */
    ReturnData insert(Contract contract);

    /**
      * 执行 Contract 删除操作
      */
    ReturnData delete(Contract contract);

    /**
      * 执行 Contract 批量删除操作
      */
    ReturnData deleteBatch(String[] ids);

    /**
      * 执行 Contract 修改操作
      */
    ReturnData update(Contract contract);

    /**
      * 执行 Contract 分页查询操作
      */
    JqGridJsonBean select(String page, String rows, String order_by, Contract contract);

    /**
      * 执行 Contract 分页查询操作 - 关联查询
      */
    JqGridJsonBean selectRelationData(String page, String rows, String order_by, Contract contract);

    /**
      * 执行 Contract 查询不分页操作
      */
    ReturnData selectByParam(String order_by, Contract contract);

    JqGridJsonBean selectRelationDataByEmpRealname(String page, String rows, String order_by, Contract contract,
                                                   String empRealname);

}