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
	
    <script src="<%=path %>/assets/js/user_common.js"></script>
    
    <style type="text/css">
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
            <li>工作管理</li>
            <li>></li>
            <li class="active">考勤数据修改</li>
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
                    <span class="col-xs-3 glyphicon">* 自增id：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <%-- <input type="text" class="col-xs-12 GL-add-require" id="id" value="<%=request.getParameter("id") %>"> --%>
                        <input type="text" class="col-xs-12 GL-add-require" id="id" value="">
                    </div>
                </div>
                
             </div>
                
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 所属员工：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <select id="empId" name="empIdName" style="width:200px;" >
                        </select>
                    </div>
                </div>
                
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 考勤状态：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <select id="attdState" name="attdStateName" style="width:200px;" >
                        	<option value="0">请先选择考勤状态</option>
                        	<option value="1">缺勤</option>
                        	<option value="2">旷工半天</option>
                        	<option value="3">正常上班</option>
                        	<option value="4">正常下班</option>
                        	<option value="5">迟到</option>
                        	<option value="6">早退</option>
                        	<option value="7">请假</option>
                        	<option value="8">调休</option>
                        	<option value="9">出差</option>
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

var attendanceParam = {};
	attendanceParam.id;
	attendanceParam.empId;
	
	attendanceParam.attdState;
	
	attendanceParam.createTime;

	$("#id").val(olddata.id);
	
	//$("#empId").val(olddata.empId);
	//员工姓名的回显
	$.ajax({
		url:'<%=path %>/employee/ajaxSelectEmpById',
   		type:'post',
   		cache:false,
   		dataType:'json',
   		data: {"empId":olddata.empId},
       	success:function(data){
       		var list = data.data.data;
		    $("#empId").html("<option value='"+olddata.empId+"'>"+list+"</option>");
       	}, 
       	error:function() {
       		alert("异常！");
       	}
    });
	
	$("#attdState").val(olddata.attdState);
	
	//alert(getFormatDateNohms(olddata.createTime));
	$("#createTime").val(getFormatDateNohms(olddata.createTime));
	
	$("#save").click(function(){
		var param = JSON.parse(JSON.stringify(attendanceParam));
		
					param.id=$("#id").val();
					param.empId=$("#empId").val();
					param.attdState=$("#attdState").val();
					param.createTime=$("#createTime").val();
				
	    $.ajax({url:'<%=path %>/attendance/update',
       		type:'post',
       		cache:false,
       		dataType:'json',
       		data: JSON.stringify(param),
       		contentType: "application/json;charset=UTF-8",
           	success:function(data){
           		if(data.code == "OK"){
           			alert("数据修改成功");
               		window.location.href= "<%=path %>/attendance/show";
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
		window.location.href= "<%=path %>/attendance/show";
	});

</script>
</html>