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
    <!--提示必填项部分-->
    <div class="filter panel panel-default">
        <div class="panel-heading" style="border-bottom:0px;">
            <span>温馨提示：带*的为必填部分，请核对完成后点击添加</span>
            <c:if test="${function == 'add'}">
            <span type = "button" id ="add" class="save">添加</span>
            </c:if>
            <c:if test="${function != 'add'}">
            <span type = "button" id ="save" class="save">保存</span>
            </c:if>
            <span type = "button" id= "back" class="back">返回</span>

        </div>
    </div>


    <div class="filter panel panel-default">
        <div class="panel-heading">
            <span>出差信息</span>

            <div class="GL-danger-info" id="GLDangerInfo">

            </div>
        </div>

        <!--添加 -->
        <div class="panel-body pad-tb-25" id="jcxx">
            <div class="row">
                <c:if test="${function != 'add'}">
                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 员工id：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="number" class="col-xs-12 GL-add-require" id="id" value="${olddata.id}" readonly="readonly">
                    </div>
                </div>
                </c:if>

                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 员工编码：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <c:if test="${function == 'add'}">
                        <input type="number" class="col-xs-12 GL-add-require" id="empId" value="${olddata.empId}" >
                        </c:if>
                        <c:if test="${function != 'add'}">
                        <input type="number" class="col-xs-12 GL-add-require" id="empId" value="${olddata.empId}" readonly="readonly">
                        </c:if>
                    </div>
                </div>
                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 员工姓名：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <c:if test="${function == 'add'}">
                        <input type="text" class="col-xs-12 GL-add-require" id="empName" value="${olddata.empName}" >
                        </c:if>
                        <c:if test="${function != 'add'}">
                        <input type="text" class="col-xs-12 GL-add-require" id="empName" value="${olddata.empName}" readonly="readonly">
                        </c:if>
                    </div>
                </div>
                <div class="col-xs-6 row ie-col-6" style="margin-left: 10px">
                    <span class="col-xs-3 glyphicon" >* 出差开始时间：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="demo-input" placeholder="请选择出差开始时间" id="evectionTimebegin" style="width:350px;" value="${olddata.evectionTimebegin}">

                    </div>
                </div>
                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 出差结束时间：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="demo-input" placeholder="请选择出差结束时间" id="evectionTimeover" style="width:350px;" value="${olddata.evectionTimeover}">

                    </div>
                </div>
                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 出差理由：
                    </span>
                    <input type="text" class="col-xs-12 GL-add-require" id="evectionReason" style="width:380px;" value="${olddata.evectionReason}">
                </div>
            </div>
           </div>



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



    var evectionParam ={};
    evectionParam.id;
    evectionParam.empId;
    evectionParam.empName;
    evectionParam.evectionTimebegin;
    evectionParam.evectionTimeover;
    evectionParam.evectionReason;



    $("#save").click(function () {
        var param = JSON.parse(JSON.stringify(evectionParam));
        param.id = $("#id").val();
        param.empId = $("#empId").val();
        param.empName = $("#empName").val();
        param.evectionTimebegin = $("#evectionTimebegin").val();
        param.evectionTimeover = $("#evectionTimeover").val();
        param.evectionReason = $("#evectionReason").val();

        $.ajax({
            url:'<%=path %>/employeeEvection/update',
            type:'post',
            cache:false,
            data : JSON.stringify(param),
            dataType:'json',
            contentType: "application/json;charset=UTF-8",
            success:function(data) {
                if(data.code == "OK"){
                    alert("修改数据成功！");
                    window.location.href="<%=path %>/employeeEvection/admin";
                }else{
                    alert(data.msg);
                }

            }
        });
    });

    $("#back").click(function () {
       window.location.href = "<%=path %>/employeeEvection/admin";
    });

    //根据empId查看员工是否存在
    $("#empId").change(function () {
        var empId = $("#empId").val();
        $.ajax({
            url:'<%=path %>/employeeEvection/selectByEmpId',
            type:'post',
            cache:false,
            data : {"empId":empId},
            dataType:'json',
            success:function(data) {
            if(data.realname != "ERROR"){
                $("#empName").val(data.realname);
            }else {
                alert("员工不存在，请重新填写！");
                $("#empId").val("");
                $("#empName").val("");
            }
            }
        });

    });

    $("#add").click(function () {
        var param = JSON.parse(JSON.stringify(evectionParam));
        param.empId = $("#empId").val();
        param.empName = $("#empName").val();
        param.evectionTimebegin = $("#evectionTimebegin").val();
        param.evectionTimeover = $("#evectionTimeover").val();
        param.evectionReason = $("#evectionReason").val();
        $.ajax({
            url:'<%=path %>/employeeEvection/insert',
            type:'post',
            cache:false,
            data : JSON.stringify(param),
            dataType:'json',
            contentType: "application/json;charset=UTF-8",
            success:function(data) {
                if(data.code == "OK"){
                    alert("插入数据成功！");
                    window.location.href="<%=path %>/employeeEvection/admin";
                }else{
                    alert("插入数据失败！");
                }
            }
        });
    });




</script>
</body>