<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>公司OA系统</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="<%=path %>/assets/css/iframe.css">

    <link rel="stylesheet" href="<%=path %>/assets/css/ui.jqgrid.css">

    <script src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script src="<%=path %>/assets/js/jquery/grid.locale-cn.js"></script>
    <script src="<%=path %>/assets/js/jquery/jquery.jqGrid.min.js"></script>
    <script src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>

    <script src="<%=path %>/assets/js/user_common.js"></script>

    <style>
        span.glyphicon{
            height:30px;
            line-height:30px;
        }

        #fieldHidden{
            display: none;
        }
    </style>
</head>
<body>
<div class="container-fluid GL-hzs">
    <!--头部内容-->
    <div class="header">
        <ol class="breadcrumb">
            <li>人事信息管理</li>
            <li>></li>
            <li class="active">用户信息管理</li>
        </ol>
    </div>
    <!--过滤条件-->
    <div class="filter panel panel-default">
        <div class="panel-heading">
            <span class="glyphicon glyphicon-search"></span>
            <span>查询</span>
        </div>
        <div class="panel-body pad-tb-25">
            <span>用户名：</span>
            <input type="text" placeholder="请输入用户名" id="searchSelectRealname">
            <button class="chaxun-bottom" id="employee_chaxun">查询</button>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
                <span class="iconstate left bg-filter"></span>
                <span class="left bg-filter">用户信息</span>
                <%-- <shiro:hasPermission name="employee_insert">
                </shiro:hasPermission> --%>
                <button class="tianjia-button right bg-filter" id ="emp_plus"><span class="glyphicon glyphicon-plus"></span> 添加</button>
                <button class="tianjia-button right bg-filter" id ="emp_edit"><span class="glyphicon glyphicon-edit"></span> 修改</button>
                <button class="tianjia-button right bg-filter" id ="emp_remove"><span class="glyphicon glyphicon-remove"></span> 删除</button>
            </div>
        </div>
        <div class="panel-body">
            <table id="GRIDTABLE" style="border-collapse: collapse"></table>
            <div id="GRIDPAGE"></div>
        </div>
    </div>
</div>
<script>
    var employeeParam = {};
    employeeParam.id;
    employeeParam.empCode;
    employeeParam.loginname;
    employeeParam.password;
    employeeParam.realname;
    employeeParam.entryTime;
    employeeParam.jobposId;
    employeeParam.registerTime;
    $(function(){
        var GridParam = JSON.parse(JSON.stringify(employeeParam));
        $("#GRIDTABLE").jqGrid({
            //caption:'权限管理',
            url: '<%=path %>/employee/selectRelationData',
            styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式
            datatype: "json", //数据类型
            mtype: "post",//提交方式
            postData: {GridParam: JSON.stringify(GridParam)},
            //width: $(".jqGrid_wrapper").css("width"),
            autowidth: true,//自动宽
            //shrinkToFit: true,
            height: '70%',//高度，表格高度。可为数值、百分比或'auto'
            //sortorder: 'asc',
            viewrecords: true,//是否在浏览导航栏显示记录总数
            altRows: true,//设置为交替行表格,默认为false
            //rownumbers : true,//是否显示行号
            //rownumWidth : '80px', //设置行号的宽度

            multiselect: true,//定义多选选择框
            multiboxonly : true,//单选框

            colNames: [
                "",
                "员工编码",
                "用户名",
                //"用户密码",
                "真实姓名",
                "入职时间",
                "所属职位",
                "注册时间"
            ],
            colModel: [
                {name: "id", index: "id", sortable: false, width: 60, align: "center", hidden:true},
                {name: "emp_code", index: "emp_code", sortable: false, width: 60, align: "center"},
                {name: "loginname", index: "loginname", sortable: false, width: 60, align: "center"},
                //{name: "password", index: "password", sortable: false, width: 60, align: "center"},
                {name: "realname", index: "realname", sortable: false, width: 60, align: "center"},
                {name: "entry_time", index: "entry_time", sortable: false, width: 60, align: "center"},
                {name: "jobpos_name", index: "jobpos_name", sortable: false, width: 60, align: "center"},
                {name: "register_time", index: "register_time", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
                    return getFormatDate(value);
                }}
            ],
            viewrecords: true, //是否在浏览导航栏显示记录总数
            rowNum:15,
            rowList:[15,30,50],
            //loadonce: true,
            jsonReader : {
                root:"root", //结果集
                page: "page", //第几页
                total: "total", //总页数
                records: "records", //数据总数
                repeatitems: false
            },
            pager: "#GRIDPAGE"
        });
        $("#GRIDPAGE").css("height", "45px");
    });

    var searchGridParam = JSON.stringify(employeeParam);

    //查询
    $("#employee_chaxun").click(function(){
        var param = JSON.parse(searchGridParam);

        param.realname= $("#searchSelectRealname").val();

        //为param 赋值
        var GridParam = JSON.stringify(param);
        searchFun(GridParam);
    });

    function searchFun(GridParam){
        $("#GRIDTABLE").jqGrid("setGridParam",{
            url:"<%=path %>/employee/selectRelationData",
            postData:{GridParam:GridParam},
            page:1
        }).trigger("reloadGrid");
    }

    //新增
    $("#emp_plus").click(function(){
        window.location.href= "<%=path %>/employee/add";
    });


    //修改 - 判定只能修改一条数据
    $("#emp_edit").click(function(){
        var ids = $("#GRIDTABLE").jqGrid("getGridParam","selarrrow");
        if(ids.length == 0){
            alert("先选择一条数据");
            return;
        } else if(ids.length > 1){
            alert("请您只选择一条需要修改的数据");
            return;
        } else {
            if (confirm("确认修改当前选中数据的信息吗？")) {
                //暂时不涉及经纬度的信息加载 - 暂不涉及相关网格码的修改
                window.location.href= "<%=path %>/employee/edit?id="+ids;
            }
        }
    });


    /*
    删除 - 支持批量选中的删除 - 支持联动删除别的表中的数据
    */
    $("#emp_remove").click(function(){
        var ids = $("#GRIDTABLE").jqGrid("getGridParam","selarrrow");
        //alert(ids);//3,2,1
        if(ids == ""){
            alert("先选择一条数据");
            return;
        } else {
            if (confirm("确认删除当前选中数据吗？")) {
                $.ajax({url:'<%=path %>/employee/deleteBatch',
                    type:'post',
                    cache:false,
                    dataType:'json',
                    data:{
                        ids:ids+""
                    },
                    success:function(data){
                        if(data.code == "OK"){
                            alert("数据删除成功");
                            window.location.reload();
                        } else {
                            alert(data.msg);
                        }
                    },
                    error : function() {
                        alert("异常！");
                    }
                });
            }
        }
    });

</script>
</body>