package com.pengyd.dao;

import java.util.List;
import java.util.Map;

import com.pengyd.bean.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @Author pengyd
 * @Date 2018/3/22 16:32
 * @function:
 */
@Repository
public interface PermissionMapper {

    /**
     * permission 执行插入 Permission 操作
     */
    void insert(Permission permission);

    /**

     */
    void delete(Permission permission);

    /**
     * permission 执行 批量删除 数据操作
     */
    void deleteBatch(@Param("ids") String[] ids);

    /**
     * permission 执行修改 数据操作
     */
    void update(Permission permission);

    /**
     * 根据条件查询Permission总数据量
     */
    int selectCount(Permission permission);

    /**
     * 根据条件查询Permission数据
     */
    List<Permission> selectData(@Param("permission") Permission permission, @Param("limit") int limit,
                                @Param("offset") int offset, @Param("order_by") String order_by);

    /**
     * 根据条件查询Permission总数据量 - 关联查询
     */
    int selectRelationCount(Permission permission);

    /**
     * 根据条件查询Permission数据 - 关联查询
     */
    List<Map<String, Object>> selectRelationData(@Param("permission") Permission permission, @Param("limit") int limit,
                                                 @Param("offset") int offset, @Param("order_by") String order_by);

    /**
     * 根据条件查询Permission数据不分页
     */
    List<Permission> selectByParam(@Param("permission") Permission permission, @Param("order_by") String order_by);

    List<Permission> ajaxSelectPermListByUse();

    List<Permission> selectByPermIds(@Param("permIds") String permIds);

}