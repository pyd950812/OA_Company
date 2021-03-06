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
            <li class="active">职位信息新增</li>
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
                    <span class="col-xs-3 glyphicon">* 职位名称：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="jobposName" value="">
                    </div>
                </div>
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 职位编码：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="jobposCode" value="">
                    </div>
                </div>
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 职位层级：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="jobposLevel" value="">
                    </div>
                </div>
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 所属部门：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <select id="deptId" style="width:200px;" >
                        	
                        </select>
                    </div>
                </div>
                
        	 </div>
        </div>
	
	</div>
</div>
</body>

<script type="text/javascript">

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

var jobposParam = {};
	jobposParam.jobposName;
	jobposParam.jobposCode;
	jobposParam.jobposLevel;
	jobposParam.deptId;

	$("#save").click(function(){
		var param = JSON.parse(JSON.stringify(jobposParam));
		
					param.jobposName=$("#jobposName").val();
					param.jobposCode=$("#jobposCode").val();
					param.jobposLevel=$("#jobposLevel").val();
					param.deptId=$("#deptId").val();
				
	    $.ajax({url:'<%=path %>/jobpos/insert',
       		type:'post',
       		cache:false,
       		dataType:'json',
       		data: JSON.stringify(param),
       		contentType: "application/json;charset=UTF-8",
           	success:function(data){
           		if(data.code == "OK"){
           			alert("数据保存成功");
               		window.location.href= "<%=path %>/jobpos/show";
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
		window.location.href= "<%=path %>/jobpos/show";
	});

</script>
</html>