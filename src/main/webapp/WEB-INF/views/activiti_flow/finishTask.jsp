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
    
    <link rel="stylesheet" href="<%=path %>/assets/css/ui.jqgrid.css">
	
    <link rel="stylesheet" href="<%=path %>/assets/css/common.css">
    
    <%-- <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/fileinput/fileinput.min.css"> --%>
    
    <%-- <script src="<%=path %>/assets/js/bootstrap/fileinput/fileinput.min.js"></script>
    <script src="<%=path %>/assets/js/bootstrap/fileinput/fileinput_locale_zh.js"></script> --%>
    
    <script type="text/javascript" src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/js/jquery/grid.locale-cn.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/jquery/jquery.jqGrid.min.js"></script>
    <script src="<%=path %>/assets/js/jquery/jquery.pagination.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>

    <script src="<%=path %>/assets/js/layer/laydate.js"></script>
    
    <script src="<%=path %>/assets/js/user_common.js"></script>
    
   <style>
	.amap-sug-result{
		z-index:100000;
	}
	/* #fieldHidden{
		display: none;
	} */
	</style>
	
   </head>
   
<body>
<div class="container-fluid GL-hzs">
	<!--头部内容-->
    <div class="header">
        <ol class="breadcrumb">
            <li>工作管理</li>
            <li>></li>
            <li class="active">工作任务管理</li>
        </ol>
    </div>
    <!--提示必填项部分-->
	<div class="filter panel panel-default">
		<div class="panel-heading" style="border-bottom:0px;" id="spanDivId">
			
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
                    <span class="col-xs-3 glyphicon">* 工作小结：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <textarea class="col-xs-12 GL-add-require" id="job_work_info" value=""></textarea>
                    </div>
                </div>
                
        	 </div>
        	 
        </div>
	
	</div>
	
</div>
<script>

$(function(){
	var outcomeListSize = '${outcomeListSize}';
	
	var actHtmlStr = "<span>温馨提示：带*的为必填部分，请核对完成后点击保存</span>";
	if (outcomeListSize != null && outcomeListSize>0) {
		actHtmlStr += "<span type = 'button' id ='save' class='save'>完成</span>";
	}
	actHtmlStr += "<span type = 'button' id ='back' class='back'>返回</span>";
	
	$("#spanDivId").html(actHtmlStr);
	
	var jobsManageId = '${jobsManageId}';
	var taskId = '${taskId}';
	
	$("#save").click(function(){
		var jobWorkInfo = $("#job_work_info").val();
		//alert(annotation);

	    $.ajax({url:'<%=path %>/jobs_manage/finishTask',
       		type:'post',
            data: {jobWorkInfo:jobWorkInfo,
            	jobsManageId:jobsManageId,
            	taskId:taskId}, 
           	success:function(data){
           		if(data.code == "OK"){
           			alert("任务处理完毕");
               		window.location.href= "<%=path %>/activiti_flow/showTask";
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
		window.location.href= "<%=path %>/activiti_flow/showTask";
	});

});

</script>
</body>