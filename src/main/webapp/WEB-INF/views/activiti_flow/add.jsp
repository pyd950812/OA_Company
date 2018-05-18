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
    
    <%-- <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/fileinput/fileinput.min.css"> --%>
    
    <script src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script src="<%=path %>/assets/js/jquery/jquery.pagination.js"></script>
    <script src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>

    <script src="<%=path %>/assets/js/layer/laydate.js"></script>
    
    <%-- <script src="<%=path %>/assets/js/bootstrap/fileinput/fileinput.min.js"></script>
    <script src="<%=path %>/assets/js/bootstrap/fileinput/fileinput_locale_zh.js"></script> --%>
    
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
            <li>工作管理</li>
            <li>></li>
            <li class="active">业务流程部署</li>
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
                    <span class="col-xs-3 glyphicon">* 流程名称：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="activitiFlowId" name="activitiFlowName" value="">
                    </div>
                </div>
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 流程文件-ZIP：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <!-- <input type="text" class="col-xs-12 GL-add-require" id="activitiFlowFile" value=""> -->
                        <!-- <input type="file" name="activitiFlowFile" id="activitiFlowFile" multiple class="file-loading" />
                        multiple   data-show-upload="true" data-show-caption="true" 
                         -->
                        
                        <input id="activitiFlowFileId" name="activitiFlowFileName" type="file" class="file">
                    </div>
                </div>
        	 </div>
        </div>
	
</form>
      
	</div>
</div>
<script>

	$("#save").click(function(){

		var formData = new FormData($('#uploadForm')[0]);
		
	    $.ajax({url:'<%=path %>/activiti_flow/insertActivitiFlow',
       		type:'post',
            data: formData, 
       		cache:false,
       		//JSON.stringify(param),
            processData: false,  
            contentType: false,
            dataType: 'json',
           	success:function(data){
           		if(data.code == "OK"){
           			alert("服务部署成功");
               		window.location.href= "<%=path %>/activiti_flow/show";
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
		window.location.href= "<%=path %>/activiti_flow/show";
	});

</script>
</body>