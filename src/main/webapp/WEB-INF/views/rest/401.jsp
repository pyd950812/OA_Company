<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>401</title>
    <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/bootstrap.min.css">
</head>
<body style="background-color: #2e94ce">
	<div class="container-fluid">
		<div class="row" style="padding-top:100px;">
		    <div class="col-xs-8 col-xs-offset-2">
		    	<img src="<%=path %>/assets/image/401.png" class="img-responsive center-block"/>
		    </div>
		    <div class="col-xs-8 col-xs-offset-2">
		    	<p style="font-size: 20px;text-align: center;color: black;padding-top:20px;">您没有操作权限，点击返回首页！</p>
		    </div>
		    <div class="col-xs-8 col-xs-offset-2" style="text-align:center;padding-top:20px;">
		    	<button style="background:#CA4747; padding:6px 20px;color:white;" class="btn" onclick="window.parent.location.href='<%=path %>/rest/logoutAction'">返回首页</button>
		    </div>
		</div>
	</div>
</body>
</html>