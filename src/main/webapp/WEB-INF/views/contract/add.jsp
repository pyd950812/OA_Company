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
            <li>人事信息管理</li>
            <li>></li>
            <li class="active">合同信息新增</li>
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
        
        
<form id="uploadForm" enctype="multipart/form-data">  

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
                    <span class="col-xs-3 glyphicon">* 合同上传：
                    </span>
                    <form name="Form2" action="/SpringMVC006/springUpload" method="post"  enctype="multipart/form-data">
						<input type="file" name="contractFileName" id="f_upload" class="file" />
					</form>
                </div>
                
                
				<!-- <div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 合同存储的路径：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="contractUrl" value="">
                    </div>
                </div>
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 合同文件名称：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="contractName" value="">
                    </div>
                </div> -->
                
        	 </div>
        </div>
        
</form>
        
	</div>
</div>
</body>

<script type="text/javascript">

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

var contractParam = {};
	contractParam.empId;

	$("#save").click(function(){
		/* var param = JSON.parse(JSON.stringify(contractParam));
		
					param.empId=$("#empId").val();
					
					param.contractUrl=$("#contractUrl").val();
					param.contractName=$("#contractName").val(); */
		
		var formData = new FormData($('#uploadForm')[0]);
		
	    $.ajax({url:'<%=path %>/contract/insert',
       		type:'post',
            data: formData, 
       		cache:false,
       		data: formData,
       		//JSON.stringify(param),
            processData: false,  
            contentType: false, 
           	success:function(data){
           		if(data.code == "OK"){
           			alert("数据保存成功");
               		window.location.href= "<%=path %>/contract/show";
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
		window.location.href= "<%=path %>/contract/show";
	});

</script>
</html>