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

    <script src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script src="<%=path %>/assets/js/jquery/jquery.pagination.js"></script>
    <script src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=path %>/assets/js/jquery/jquery.jqGrid.min.js"></script>
    <script src="<%=path %>/assets/js/layer/laydate.js"></script>
    <script src="<%=path %>/assets/js/user_common.js"></script>

    <style>
        .amap-sug-result{
            z-index:100000;
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
            <li class="active">用户信息修改</li>
        </ol>
    </div>
    <!--提示必填项部分-->
	<div class="filter panel panel-default">
		<div class="panel-heading" style="border-bottom:0px;">
			<span>温馨提示：带*的为必填部分，请核对完成后点击添加</span>
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
                             <input type="text" class="col-xs-12 GL-add-require" id="id" value="">
                         </div>
                     </div>

                 </div>

                 <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 员工编码：
                    </span>
                     <div class="col-xs-9 pad-0 row">
                         <input type="text" class="col-xs-12 GL-add-require" id="empCode" value="">
                     </div>
                 </div>
                 <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 用户名：
                    </span>
                     <div class="col-xs-9 pad-0 row">
                         <input type="text" class="col-xs-12 GL-add-require" id="loginname" value="">
                     </div>
                 </div>
                 <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 用户密码：
                    </span>
                     <div class="col-xs-9 pad-0 row">
                         <input type="text" class="col-xs-12 GL-add-require" id="password" value="">
                     </div>
                 </div>
                 <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 真实姓名：
                    </span>
                     <div class="col-xs-9 pad-0 row">
                         <input type="text" class="col-xs-12 GL-add-require" id="realname" value="">
                     </div>
                 </div>

                 <!-- <div class="col-xs-6 row ie-col-6">
                     <span class="col-xs-3 glyphicon">* 注册时间：
                     </span>
                     <div class="col-xs-9 pad-0 row">
                         <input type="text" class="col-xs-12 GL-add-require" id="registerTime" value="">
                     </div>
                 </div> -->

                 <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 入职时间：
                    </span>
                     <input type="text" class="demo-input" placeholder="请选择入职日期" id="entryTime" style="width:200px;" >

                 </div>

                 <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 所属部门：
                    </span>
                     <div class="col-xs-9 pad-0 row">
                         <!-- <input type="text" class="col-xs-12 GL-add-require" id="deptId" value=""> -->
                         <select id="deptId" style="width:200px;" >

                         </select>
                     </div>
                 </div>

                 <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 所属职位：
                    </span>
                     <div class="col-xs-9 pad-0 row">
                         <!-- <input type="text" class="col-xs-12 GL-add-require" id="jobposId" value=""> -->
                         <select id="jobposId" style="width:200px;" >
                             <option value="-1">请先选择所属部门</option>
                         </select>
                     </div>
                 </div>

                 </div>

        	 </div>
        </div>
	
	</div>
</div>
<script>
    //执行一个laydate实例
    laydate.render({
        elem: '#entryTime' //指定元素
    });
    var employeeParam = {};
    employeeParam.id;
    employeeParam.empCode;
    employeeParam.loginname;
    employeeParam.password;
    employeeParam.realname;
    employeeParam.entryTime;
    employeeParam.jobposId;
    employeeParam.registerTime;
    $("#id").val(olddata.id);
    $("#empCode").val(olddata.empCode);
    $("#loginname").val(olddata.loginname);
    $("#password").val(olddata.password);
    $("#realname").val(olddata.realname);

    //alert(getFormatDateNohms(olddata.entryTime));
    $("#entryTime").val(getFormatDateNohms(olddata.entryTime));

    //回显相关的工作信息

    //获取工作对应的部门ID
    $.ajax({
        url:'<%=path %>/jobpos/ajaxSelectJobposById',
        type:'post',
        cache:false,
        dataType:'json',
        data: {"id":olddata.jobposId},
        success:function(data){
            var list1 = data.data.data[0];
            var oldDeptId = list1.deptId;
            //alert(oldDeptId);

            /* 获取到部门信息集合 */
            $.ajax({
                url:'<%=path %>/department/ajaxSelectDept',
                type:'post',
                cache:false,
                dataType:'json',
                contentType: "application/json;charset=UTF-8",
                success:function(data){
                    var list2 = data.data.data;
                    var _html = "<option value='0'>请选择所属部门</option>";
                    for(var i=0;i<list2.length;i++){
                        if (oldDeptId == list2[i].id) {
                            _html += "<option value='"+list2[i].id+"' selected>"+list2[i].deptname+"</option>";
                        } else {
                            _html += "<option value='"+list2[i].id+"'>"+list2[i].deptname+"</option>";
                        }
                    }
                    $("#deptId").html(_html);

                    $.ajax({
                        url:'<%=path %>/jobpos/ajaxSelectJobposByDeptId',
                        type:'post',
                        cache:false,
                        dataType:'json',
                        data: {"deptId":oldDeptId},
                        success:function(data){
                            var list3 = data.data.data;
                            var _html = "<option value='0'>请选择所属职位</option>";
                            for(var i=0;i<list3.length;i++){
                                if (olddata.jobposId == list3[i].id) {
                                    _html += "<option value='"+list3[i].id+"' selected>"+list3[i].jobpos_name+"</option>";
                                } else {
                                    _html += "<option value='"+list3[i].id+"'>"+list3[i].jobpos_name+"</option>";
                                }
                            }
                            $("#jobposId").html(_html);
                        },
                        error:function() {
                            alert("异常！");
                        }
                    });
                },
                error:function() {
                    alert("异常！");
                }
            });


        },
        error:function() {
            alert("异常！");
        }
    });

    //定义部门与所属工作信息的联动查询实现
    $("#deptId").change(function(){
        var deptId = $("#deptId").val()
        //lert(deptId)
        $.ajax({
            url:'<%=path %>/jobpos/ajaxSelectJobposByDeptId',
            type:'post',
            cache:false,
            dataType:'json',
            data: {"deptId":deptId},
            success:function(data){
                var list = data.data.data;
                var _html = "<option value='0'>请选择所属职位</option>";
                for(var i=0;i<list.length;i++){
                    _html += "<option value='"+list[i].id+"'>"+list[i].jobpos_name+"</option>";
                }
                $("#jobposId").html(_html);
            },
            error:function() {
                alert("异常！");
            }
        });
    });

    $("#save").click(function(){
        var param = JSON.parse(JSON.stringify(employeeParam));
        param.id=$("#id").val();
        param.empCode=$("#empCode").val();
        param.loginname=$("#loginname").val();
        param.password=$("#password").val();
        param.realname=$("#realname").val();
        param.entryTime=$("#entryTime").val();
        param.jobposId=$("#jobposId").val();

        $.ajax({url:'<%=path %>/employee/update',
            type:'post',
            cache:false,
            dataType:'json',
            data: JSON.stringify(param),
            contentType: "application/json;charset=UTF-8",
            success:function(data){
                if(data.code == "OK"){
                    alert("数据修改成功");
                    window.location.href= "<%=path %>/employee/show";
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
        window.location.href= "<%=path %>/employee/show";
    });
</script>
</body>