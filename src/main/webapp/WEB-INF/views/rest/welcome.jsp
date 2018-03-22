<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"> 
    <base href="<%=basePath%>">
    <title>welcome</title>
    <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path %>/assets/css/index.css">
    <script src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
    <script src="<%=path %>/assets/js/html5shiv.min.js"></script>
    <script src="<%=path %>/assets/js/respond.min.js"></script>
    <![endif]-->
    
    <style type="text/css">
    	#welcomeDiv{
    		width: 100%;
    		height: 100%;
    	}
    	#welcomeDiv img {
    		width: 100%;
    		height: 100%;
    	}
    
    </style>
</head>

<body>
    <div id="welcomeDiv">
        <img src="<%=path %>/assets/image/welcome.jpg">
    </div>
</body>