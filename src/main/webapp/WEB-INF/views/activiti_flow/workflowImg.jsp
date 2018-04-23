<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
	
    <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="<%=path %>/assets/css/iframe.css">
	
    <link rel="stylesheet" href="<%=path %>/assets/css/ui.jqgrid.css">
	
    <script type="text/javascript" src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/js/jquery/grid.locale-cn.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/jquery/jquery.jqGrid.min.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>

    <script src="<%=path %>/assets/js/user_common.js"></script>
    
    <script type="text/javascript" src="<%=path %>/assets/js/layer/laydate.js"></script>
	
	<style type="text/css">
		span.glyphicon{
			height:30px;
			line-height:30px;
		}
		
		#attendance_add{
			margin-left: 100px;
		}
	</style>
	
</head>

<body>
	<div id="divId">
	</div>
</body>

	<script type="text/javascript">
		var deploymentId = '${deploymentId}';
		var imageName = '${imageName}';
		var acs = '${acs}';
		
		/* alert(deploymentId);
		alert(imageName);
		alert(acs); */
		
		var acsJson = JSON.parse(acs);

		//1.获取到规则流程图
		var htmlStr = "<img style='position: absolute;top: 0px;left: 0px;' src='<%=path %>/activiti_flow/viewImage?pdid="+deploymentId+"&imageName="+imageName+"'>";
		
		//2.根据当前活动的坐标，动态绘制DIV
		htmlStr += "<div style='position: absolute;border:1px solid red;top:"+acsJson.y+"px;left: "+acsJson.x+"px;width: "+acsJson.width+"px;height:"+acsJson.height+"px; '></div>";
		
		$("#divId").html(htmlStr);
	</script>
</html>