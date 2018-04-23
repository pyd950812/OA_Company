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
            <li>工作管理</li>
            <li>></li>
            <li class="active">考勤数据补卡</li>
        </ol>
    </div>
    <!--提示必填项部分-->
	<div class="filter panel panel-default">
		<div class="panel-heading" style="border-bottom:0px;">
			<span>温馨提示：带*的为必填部分，请核对完成后点击保存</span>
			
	<span type = "button" id ="save" class="save">补卡</span>
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
                    <span class="col-xs-3 glyphicon">* 所属部门：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <!-- <input type="text" class="col-xs-12 GL-add-require" id="deptId" value=""> -->
                        <select id="deptId" name="eptIdName" style="width:200px;" >
                        	
                        </select>
                    </div>
                </div>
        	 	
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 所属职位：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <!-- <input type="text" class="col-xs-12 GL-add-require" id="jobposId" value=""> -->
                        <select id="jobposId" name="jobposIdName" style="width:200px;" >
                        	<option value="-1">请先选择所属部门</option>
                        </select>
                    </div>
                </div>
        	 	
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 所属员工：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <select id="empId" name="empIdName" style="width:200px;" >
                        	<option value="-1">请先选择所属职位和部门</option>
                        </select>
                    </div>
                </div>
                
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 考勤状态：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <select id="attdState" name="attdStateName" style="width:200px;" >
                        	<option value="0">请先选择考勤状态</option>
                        	<!-- <option value="1">缺勤</option> -->
                        	<option value="2">旷工半天</option>
                        	<option value="3">正常上班</option>
                        	<option value="4">正常下班</option>
                        	<option value="5">迟到</option>
                        	<option value="6">早退</option>
                        	<!-- <option value="7">请假</option>
                        	<option value="8">调休</option>
                        	<option value="9">出差</option> -->
                        </select>
                    </div>
                </div>
                
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 考核日期：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="createTime" placeholder="请输入日期" value="">
                    </div>
                </div>
        	 	
                
        	 </div>
        </div>
	
	</div>
</div>
</body>

<script type="text/javascript">

//执行一个laydate实例
laydate.render({
	elem: '#createTime' //指定元素
});

$(function(){
	/* 获取到部门信息集合 */
	$.ajax({
		url:'<%=path %>/department/ajaxSelectDept',
   		type:'post',
   		cache:false,
   		dataType:'json',
   		contentType: "application/json;charset=UTF-8",
       	success:function(data){
       		var list = data.data.data;
       		var _html = "<option value='0'>请选择所属部门</option>";
		    for(var i=0;i<list.length;i++){
		    	_html += "<option value='"+list[i].id+"'>"+list[i].deptname+"</option>";
		    }
		    $("#deptId").html(_html);
       	}, 
       	error:function() {
       		alert("异常！");
       	}
    });

	//定义部门与所属工作信息的联动查询实现
	$("#deptId").change(function(){
		var deptId = $("#deptId").val()
		//alert(deptId)
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

	//定义职位与所属员工信息的联动查询实现
	$("#jobposId").change(function(){
		var jobposId = $("#jobposId").val()
		//alert(deptId)
		$.ajax({
			url:'<%=path %>/employee/ajaxSelectEmpByJobposId',
	   		type:'post',
	   		cache:false,
	   		dataType:'json',
	   		data: {"jobposId":jobposId},
	       	success:function(data){
	       		var list = data.data.data;
	       		var _html = "<option value='0'>请选择所属员工</option>";
			    for(var i=0;i<list.length;i++){
			    	_html += "<option value='"+list[i].id+"'>"+list[i].realname+"</option>";
			    }
			    $("#empId").html(_html);
	       	}, 
	       	error:function() {
	       		alert("异常！");
	       	}
	    });
	});
	
});

var attendanceParam = {};
	attendanceParam.empId;
	attendanceParam.attdState;
	attendanceParam.createTime;

	$("#save").click(function(){
		var param = JSON.parse(JSON.stringify(attendanceParam));
		
					param.id=$("#id").val();
					param.empId=$("#empId").val();
					param.attdState=$("#attdState").val();
					param.createTime=$("#createTime").val();
				
	    $.ajax({url:'<%=path %>/attendance/insertByTime',
       		type:'post',
       		cache:false,
       		dataType:'json',
       		data: JSON.stringify(param),
       		contentType: "application/json;charset=UTF-8",
           	success:function(data){
           		if(data.code == "OK"){
           			alert("补卡成功");
               		window.location.href= "<%=path %>/attendance/show";
           		} else {
           			alert(data.msg);
           		}
           	},
           	error : function() {
           		alert("补卡异常！");
           	}
       });
	});
	
	$("#back").click(function(){
		window.location.href= "<%=path %>/attendance/show";
	});

</script>
</html>