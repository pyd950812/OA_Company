<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>公司OA系统</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path %>/assets/css/iframe.css">
    <link rel="stylesheet" href="<%=path %>/assets/css/pagination.css">

    <link rel="stylesheet" href="<%=path %>/assets/css/common.css">

    <script src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script src="<%=path %>/assets/js/jquery/jquery.pagination.js"></script>
    <script src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>

    <script src="<%=path %>/assets/js/layer/laydate.js"></script>

</head>

<style>
    .amap-sug-result{
        z-index:100000;
    }
    /* #fieldHidden{
        display: none;
    } */
</style>
<body>
<div class="container-fluid GL-hzs">
    <!--头部内容-->
    <div class="header">
        <ol class="breadcrumb">
            <li><a>首页</a></li>
            <li>></li>
            <li>薪资管理</li>
            <li>></li>
            <li class="active">员工出差管理</li>
        </ol>
    </div>


    <div class="filter panel panel-default">
        <div class="panel-heading">
            <span>出差信息</span>
                <div id="evec_over">
                    <c:if test="${sessionScope.name == 'admin' }">
                    <button class="tianjia-button right bg-filter" id ="emp_plus"><span class="glyphicon glyphicon-plus"></span> 添加</button>
                    <button class="tianjia-button right bg-filter" id ="emp_edit"><span class="glyphicon glyphicon-edit"></span> 修改</button>
                    <button class="tianjia-button right bg-filter" id ="emp_remove"><span class="glyphicon glyphicon-remove"></span> 删除</button>
                    </c:if>
                </div>
            <div class="GL-danger-info" id="GLDangerInfo">

            </div>
        </div>
        <!--添加 -->
        <c:if test="${sessionScope.name == 'admin' }">
        <div class="panel-body pad-tb-25" id="jcxx">
            <div class="row">
                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 员工编码：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="number" class="col-xs-12 GL-add-require" id="empId" value="">
                    </div>
                </div>
                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 员工姓名：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="empName" value="">
                    </div>
                </div>
                <div class="col-xs-6 row ie-col-6" style="margin-left: 10px">
                    <span class="col-xs-3 glyphicon" >* 出差开始时间：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="demo-input" placeholder="请选择出差开始时间" id="evectionTimebegin" style="width:380px;" >

                    </div>
                </div>
                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 出差结束时间：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="demo-input" placeholder="请选择出差结束时间" id="evectionTimeover" style="width:380px;" >

                    </div>
                </div>
                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 出差理由：
                    </span>
                    <input type="text" class="col-xs-12 GL-add-require" id="evectionReason" style="width:380px;">
                </div>


            </div>

            <button class="tianjia-button right bg-filter" id ="evec_save"><span class="glyphicon glyphicon-plus"></span> 保存</button>
            <button class="tianjia-button right bg-filter" id ="evec_cancel"><span class="glyphicon glyphicon-edit"></span> 取消</button>

           </div>
        </c:if>


        <%-- 表格 --%>
        <div class="panel-body">
            <table id="GRIDTABLE" style="border-collapse: collapse"></table>
            <div id="GRIDPAGE"></div>
        </div>

        <div class="panel-body">
            <table id="GRIDTABLE1" style="border-collapse: collapse"></table>
            <div id="GRIDPAGE1"></div>
        </div>

    </div>
</div>
<script>
    laydate.render({
        elem: '#evectionTimebegin' //出差开始时间
    });
    laydate.render({
        elem: '#evectionTimeover' //出差结束时间
    });

    $("#evec_save").click(function () {
        alert("11111");
        $.ajax({
            url:'<%=path %>/employeeEvection/insert',
            type:'post',
            cache:false,
            data : JSON.stringify(""),
            dataType:'json',
            contentType: "application/json;charset=UTF-8",
            success:function(data) {
                alert(data);
            }
        });
    });





    var employeeEvection = {};
    employeeEvection.id;
    employeeEvection.empId;
    employeeEvection.empName;
    employeeEvection.evectionTimebegin;
    employeeEvection.evectionTimeover;
    employeeEvection.evectionReason;
    employeeEvection.createTime;
    employeeEvection.updateTime;


    $(function(){
        var GridParam = JSON.parse(JSON.stringify(employeeEvection));
        $("#GRIDTABLE").jqGrid({
            //caption:'权限管理',
            url: '<%=path %>/employeeEvection/select', //若修改url地址，可将此url对应的本地json文件删除
            styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式
            datatype: "json", //数据类型
            mtype: "post",//提交方式
            postData: {GridParam: JSON.stringify(GridParam)},
            //width: $(".jqGrid_wrapper").css("width"),,
            autowidth: true,//自动宽
            //shrinkToFit: true,
            height: '70%',//高度，表格高度。可为数值、百分比或'auto'
            sortorder: 'asc',
            viewrecords: true,//是否在浏览导航栏显示记录总数
            altRows: true,//设置为交替行表格,默认为false
            //rownumbers : true,//是否显示行号
            //rownumWidth : '80px', //设置行号的宽度

            multiselect: true,//定义多选选择框
            multiboxonly : true,//单选框

            colNames: [
                "id",
                "员工编码",
                "员工姓名",
                "出差开始时间",
                "出差结束时间",
                "出差理由",
                "创建时间",
                "修改时间"
            ],
            colModel: [
                {name: "id", index: "id", sortable: false, width: 60, align: "center", hidden:true},
                {name: "empId", index: "empId", sortable: false, width: 60, align: "center"},
                {name: "empName", index: "empName", sortable: false, width: 60, align: "center"},
                {name: "evectionTimebegin", index: "evectionTimebegin", sortable: false, width: 60, align: "center"},
                {name: "evectionTimeover", index: "evectionTimeover", sortable: false, width: 60, align: "center"},
                {name: "evectionReason", index: "evectionReason", sortable: false, width: 60, align: "center"},
                {name: "createTime", index: "createTime", sortable: false, width: 60, align: "center"},
                {name: "updateTime", index: "updateTime", sortable: false, width: 60, align: "center"}
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


</script>
</body>