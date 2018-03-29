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
    <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path %>/assets/css/iframe.css">
    <link rel="stylesheet" href="<%=path %>/assets/css/pagination.css">

    <link rel="stylesheet" href="<%=path %>/assets/css/common.css">

    <script type="text/javascript" src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/jquery/jquery.pagination.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>

    <script type="text/javascript" src="<%=path %>/assets/js/layer/laydate.js"></script>

    <style type="text/css">
        .amap-sug-result{
            z-index:100000;
        }

        .isDisClaI,.isDisClaL {
            display:inline-block;
            float:left;
            margin-left: 10px;
            margin-right: 30px;
        }
        .isDisClaL {
            margin-top: -2px;
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
            <li class="active">部门信息新增</li>
        </ol>
    </div>
    <!--提示必填项部分-->
    <div class="filter panel panel-default">
        <div class="panel-heading" style="border-bottom:0px;">
            <span>温馨提示：带*的为必填部分，请核对完成后点击保存</span>

            <span type = "button" id ="save" class="save">保存</span>
            <span type = "button" id= "back" class="back">返回</span>

        </div>
    </div>

    <div class="filter panel panel-default">
        <div class="panel-heading">
            <span>基础信息</span>
            <div class="GL-danger-info" id="GLDangerInfo">

            </div>
        </div>
        <div class="panel-body pad-tb-25" id="jcxx">
            <div class="row">

                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 部门编码：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="deptCode" value="">
                    </div>
                </div>
                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 部门名称：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="deptname" value="">
                    </div>
                </div>
                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 部门信息：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="deptinfo" value="">
                    </div>
                </div>

                <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 是否可分配
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <!-- <input type="text" class="col-xs-12 GL-add-require" id="isDis" value=""> -->

                        <input type="radio" name="isDisName" id="isDisId" class="isDisClaI" value="1" checked/><label class="isDisClaL" for="isDisId">是</label>

                        <input type="radio" name="isDisName" id="isDisNotId" class="isDisClaI" value="2"/><label class="isDisClaL" for="isDisNotId">否</label>
                    </div>
                </div>


            </div>
        </div>

    </div>
</div>
</body>

<script type="text/javascript">
    var departmentParam = {};
    departmentParam.deptCode;
    departmentParam.deptname;
    departmentParam.deptinfo;
    departmentParam.isDis;
    $("#save").click(function(){
        var param = JSON.parse(JSON.stringify(departmentParam));

        param.deptCode=$("#deptCode").val();
        param.deptname=$("#deptname").val();
        param.deptinfo=$("#deptinfo").val();

        var isDis = $("input[name='isDisName']:checked").val();
        param.isDis=isDis;

        //部门编码不可重复

        $.ajax({url:'<%=path %>/department/insert',
            type:'post',
            cache:false,
            dataType:'json',
            data: JSON.stringify(param),
            contentType: "application/json;charset=UTF-8",
            success:function(data){
                if(data.code == "OK"){
                    alert("添加部门信息成功");
                    window.location.href= "<%=path %>/department/show";
                } else {
                    alert(data.msg);
                }
            },
            error : function() {
                alert("异常！");
            }
        });
    });

    $("#back").click(function(){
        window.location.href= "<%=path %>/department/show";
    });
</script>
</html>