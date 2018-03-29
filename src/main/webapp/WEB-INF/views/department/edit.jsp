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

    <script type="text/javascript">
        var olddata = JSON.parse('${olddata}');
    </script>

</head>

<body>
<div class="container-fluid GL-hzs">
    <!--头部内容-->
    <div class="header">
        <ol class="breadcrumb">
            <li>人事信息管理</li>
            <li>></li>
            <li class="active">部门信息修改</li>
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

                <!-- 以下为隐藏字段值 -->
                <div id="fieldHidden">

                    <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 主键：
                    </span>
                        <div class="col-xs-9 pad-0 row">
                            <%-- <input type="text" class="col-xs-12 GL-add-require" id="id" value="<%=request.getParameter("id") %>"> --%>
                            <input type="text" class="col-xs-12 GL-add-require" id="id" value="">
                        </div>
                    </div>

                </div>

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
                    <div class="col-xs-9 pad-0 row" id="isDis">
                        <!-- <input type="text" class="col-xs-12 GL-add-require" id="isDis" value=""> -->

                        <input type="radio" name="isDisName" id="isDisId" class="isDisClaI" value="1"/><label class="isDisClaL" for="isDisId">是</label>

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
    departmentParam.id;
    departmentParam.deptCode;
    departmentParam.deptname;
    departmentParam.deptinfo;
    departmentParam.isDis;
    $("#id").val(olddata.id);
    $("#deptCode").val(olddata.deptCode);
    $("#deptname").val(olddata.deptname);
    $("#deptinfo").val(olddata.deptinfo);

    //$("#isDis").val(olddata.isDis);
    //是否可分配的数据的回显

    if (olddata.isDis == 1) {
        $("#isDisId").attr("checked","checked");
    } else {
        $("#isDisNotId").attr("checked","checked");
    }

    /* if (olddata.cameraTypeId == cameraTypeIdTemp) {
        _html += "<option value='"+cameraTypeIdTemp+"' selected>"+list[i].camera_type_name+"</option>";
    } else {
        $("input[name='isDisName']:checked").val(olddata.isDis);
        _html += "<option value='"+cameraTypeIdTemp+"'>"+list[i].camera_type_name+"</option>";
    } */

    $("#save").click(function(){
        var param = JSON.parse(JSON.stringify(departmentParam));

        param.id=$("#id").val();
        param.deptCode=$("#deptCode").val();
        param.deptname=$("#deptname").val();
        param.deptinfo=$("#deptinfo").val();
        var isDis = $("input[name='isDisName']:checked").val();
        param.isDis=isDis;

        $.ajax({url:'<%=path %>/department/update',
            type:'post',
            cache:false,
            dataType:'json',
            data: JSON.stringify(param),
            contentType: "application/json;charset=UTF-8",
            success:function(data){
                if(data.code == "OK"){
                    alert("部门信息修改成功");
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