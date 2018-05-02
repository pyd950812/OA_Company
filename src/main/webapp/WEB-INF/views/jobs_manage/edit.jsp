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
	
	<script type="text/javascript">
		var olddata = JSON.parse('${olddata}');

	</script>

</head>
   
<body>
<div class="container-fluid GL-hzs">
	<!--头部内容-->
    <div class="header">
        <ol class="breadcrumb">
            <li><a>首页</a></li>
            <li>></li>
            <li>工作管理</li>
            <li>></li>
            <li class="active">任务管理</li>
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
                    <span class="col-xs-3 glyphicon">* 指定员工：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <!-- <input type="text" class="col-xs-12 GL-add-require" id="workUserId" value=""> -->
                        <select id="workUserId" style="width:200px;" >
                        	<option value="-1">请先选择员工</option>
                        </select>
                    </div>
                </div>
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 工作描述：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="jobInfo" value="">
                    </div>
                </div>
                
        	 </div>
        </div>
	
	</div>
</div>
</body>

<script type="text/javascript">

$(function(){
	//获取部门经理下的所属员工
	$.ajax({
		url:'<%=path %>/employee/ajaxSelectSubEmpBySup',
   		type:'post',
   		cache:false,
   		dataType:'json',
   		contentType: "application/json;charset=UTF-8",
       	success:function(data){
       		var list = data.data.data;
       		var _html = "<option value='0'>请选择所属员工</option>";
		    for(var i=0;i<list.length;i++){
		    	if (olddata.workUserId == list[i].id) {
		    		_html += "<option value='"+list[i].id+"' selected>"+list[i].realname+"</option>";
				} else {
					_html += "<option value='"+list[i].id+"'>"+list[i].realname+"</option>";
				}
		    }
		    $("#workUserId").html(_html);
       	}, 
       	error:function() {
       		alert("异常！");
       	}
    });
	
});

var jobs_manageParam = {};
	jobs_manageParam.id;
	jobs_manageParam.workUserId;
	jobs_manageParam.jobInfo;

	$("#id").val(olddata.id);
	//$("#workUserId").val(olddata.workUserId);
	$("#jobInfo").val(olddata.jobInfo);

	$("#save").click(function(){
		var param = JSON.parse(JSON.stringify(jobs_manageParam));
		
					param.id=$("#id").val();
					param.workUserId=$("#workUserId").val();
					param.jobInfo=$("#jobInfo").val();
				
	    $.ajax({url:'<%=path %>/jobs_manage/update',
       		type:'post',
       		cache:false,
       		dataType:'json',
       		data: JSON.stringify(param),
       		contentType: "application/json;charset=UTF-8",
           	success:function(data){
           		if(data.code == "OK"){
           			alert("数据修改成功");
               		window.location.href= "<%=path %>/jobs_manage/show";
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
		window.location.href= "<%=path %>/jobs_manage/show";
	});

</script>
</html>