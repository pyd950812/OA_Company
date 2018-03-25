package com.pengyd.service;


import com.pengyd.bean.Contract;
import com.pengyd.util.JqGridJsonBean;
import com.pengyd.util.ReturnData;

/**
 * <p>服务层接口</p>
 * <p>Table: contract - </p>
 *
 * @since ${.now}
 */
public interface ContractService {

    /**
      * 执行 Contract 插入操作
      * @param contract
      * @return
      */
    ReturnData insert(Contract contract);

    /**
      * 执行 Contract 删除操作
      * @param contract
      * @return
      */
    ReturnData delete(Contract contract);

    /**
      * 执行 Contract 批量删除操作
      * @param contract
      * @return
      */
    ReturnData deleteBatch(String[] ids);

    /**
      * 执行 Contract 修改操作
      * @param contract
      * @return
      */
    ReturnData update(Contract contract);

    /**
      * 执行 Contract 分页查询操作
      * @param contract
      * @return
      */
    JqGridJsonBean select(String page, String rows, String order_by, Contract contract);

    /**
      * 执行 Contract 分页查询操作 - 关联查询
      * @param contract
      * @return
      */
    JqGridJsonBean selectRelationData(String page, String rows, String order_by, Contract contract);

    /**
      * 执行 Contract 查询不分页操作
      * @param contract
      * @param order_by
      * @return
      */
    ReturnData selectByParam(String order_by, Contract contract);

    JqGridJsonBean selectRelationDataByEmpRealname(String page, String rows, String order_by, Contract contract,
                                                   String empRealname);

}