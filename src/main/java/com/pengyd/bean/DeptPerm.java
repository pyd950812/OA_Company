package com.pengyd.bean;


import org.apache.ibatis.type.Alias;

/**
 * @Author pengyd
 * @Date 2018/3/22 16:18
 * @function:  权限
 */
@Alias("DeptPerm")
public class DeptPerm {

    /** 主键 */
    private Integer id;
    /** 部门ID */
    private Integer deptId;
    /** 权限ID */
    private Integer permId;


    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public Integer getDeptId(){
        return this.deptId;
    }
    public void setDeptId(Integer deptId){
        this.deptId = deptId;
    }

    public Integer getPermId(){
        return this.permId;
    }
    public void setPermId(Integer permId){
        this.permId = permId;
    }
 	@Override
    public String toString() {
    String toString = "DeptPerm ["+
    					"id = "+id+","+
						"dept_id = "+deptId+","+
						"perm_id = "+permId+","+
						"]";
        return toString;
	}

}