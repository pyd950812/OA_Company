package com.pengyd.bean;


import org.apache.ibatis.type.Alias;

/**
 * @Author pengyd
 * @Date 2018/3/22 16:18
 * @function:  权限信息
 */
@Alias("Permission")
public class Permission {

    /** 主键 */
    private Integer id;
    /** 权限名称 */
    private String name;
    /** 所属类别-1-菜单 2-权限 */
    private Integer type;
    /** 权限编码 */
    private String percode;
    /** 被访问的链接 */
    private String url;
    /** 所属的上级菜单ID */
    private Integer parentId;
    /** 是否可用 - 0-不可用 1-可用 */
    private Integer available;


    public Integer getId(){
        return this.id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Integer getType(){
        return this.type;
    }
    public void setType(Integer type){
        this.type = type;
    }

    public String getPercode(){
        return this.percode;
    }
    public void setPercode(String percode){
        this.percode = percode;
    }

    public String getUrl(){
        return this.url;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public Integer getParentId(){
        return this.parentId;
    }
    public void setParentId(Integer parentId){
        this.parentId = parentId;
    }

    public Integer getAvailable(){
        return this.available;
    }
    public void setAvailable(Integer available){
        this.available = available;
    }
 	@Override
    public String toString() {
    String toString = "Permission ["+
    					"id = "+id+","+
						"name = "+name+","+
						"type = "+type+","+
						"percode = "+percode+","+
						"url = "+url+","+
						"parent_id = "+parentId+","+
						"available = "+available+","+
						"]";
        return toString;
	}

}