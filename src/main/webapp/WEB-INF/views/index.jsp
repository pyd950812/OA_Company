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
    <title>公司OA系统</title>
    <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path %>/assets/css/index.css">
    <script src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=path %>/assets/js/echarts/echarts.js"></script>
    <!--[if lt IE 9]>
    <script src="<%=path %>/assets/js/html5shiv.min.js"></script>
    <script src="<%=path %>/assets/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!--头部导航栏信息-->
<nav class="navbar navbar-inverse" id="header">
    <div class="container-fluid">
        <div class="navbar-header header-left left">
            <a class="navbar-brad" href="#">
                <span>公司OA系统</span>
            </a>
        </div>
        <div class="header-right right">
            <button>
                <span class="glyphicon glyphicon-home"></span>
            </button>
            <button onclick="location.href='<%=path %>/rest/logoutAction'">
                <span class="glyphicon glyphicon-user"></span>
            </button>
        </div>
    </div>
</nav>
<!--主体部分-->
<div class="container-fluid" id="section">
    <div class="side-left">
        <ul class="nav nav-pills nav-stacked">
            <li role="presentation">
                <a href="#ssztgl-menu" class="nav-header" data-toggle="collapse"><span class="glyphicon glyphicon-folder-open"></span><span>人事信息管理</span></a>
                <ul id="ssztgl-menu" class="nav nav-list collapse">
                    <li><a onclick="jumpIframe('employee/show')"><span class="glyphicon glyphicon-menu-right"></span><span>用户信息管理</span></a></li>
                    <li><a onclick="jumpIframe('leading_enterprise/show')"><span class="glyphicon glyphicon-menu-right"></span><span>部门信息管理</span></a></li>
                	<li><a onclick="jumpIframe('other_enterprise/show')"><span class="glyphicon glyphicon-menu-right"></span><span>合同信息管理</span></a></li>
                	<li><a onclick="jumpIframe('other_enterprise/show')"><span class="glyphicon glyphicon-menu-right"></span><span>考勤信息管理</span></a></li>
                </ul>
            </li>

            <li role="presentation">
				<a class="nav-header" onclick="jumpIframe('poverty/show')" target = "plc-iframe"><span class="glyphicon glyphicon-menu-hamburger"></span>工作管理</a>
			</li>


			<li role="presentation">
				<a class="nav-header" onclick="jumpIframe('xzqh/show')" target = "plc-iframe"><span class="glyphicon glyphicon-menu-hamburger"></span>通讯管理</a>
			</li>


            <li role="presentation">
                <a href="#ssztgl-salary" class="nav-header" data-toggle="collapse"><span class="glyphicon glyphicon-folder-open"></span><span>薪资管理</span></a>
                <ul id="ssztgl-salary" class="nav nav-list collapse">
                    <li><a onclick="jumpIframe('employee/salary')"><span class="glyphicon glyphicon-menu-right"></span><span>员工薪资信息</span></a></li>
                    <li><a onclick="jumpIframe('employeeEvection/evection')"><span class="glyphicon glyphicon-menu-right"></span><span>员工出差管理</span></a></li>
                    <li><a onclick="jumpIframe('')"><span class="glyphicon glyphicon-menu-right"></span><span>员工加班管理</span></a></li>
                    <li><a onclick="jumpIframe('')"><span class="glyphicon glyphicon-menu-right"></span><span>员工处罚管理</span></a></li>
                </ul>
            </li>



        </ul>
    </div>
    <div class="side-right" style="overflow:hidden;">
        <%-- <Iframe src="<%=path %>/rest/welcome" width="100%" height="99.5%" frameborder="0" id="plc-iframe" onload="iframeonload()"></Iframe> --%>
        <Iframe src="<%=path %>/employee/add" width="100%" height="99.5%" frameborder="0" id="plc-iframe" onload="iframeonload()"></Iframe>
    </div>

</div>
<script type="text/javaScript">
	//定义全局变量
	window.gloData = {};
	(function resize(){
		$("#section").height($("body").height() - 120);
	})();
	
	//判断页面有无权限方法
    function iframeonload() {
		var childiframe = document.frames ? document.frames["plc-iframe"].document
				: document.getElementById("plc-iframe").contentDocument;
		if (null != childiframe.getElementById("hiddenLoginPage")) {
			window.location.href = '<%=path %>/rest/login';
		}
	}
	
	//跳转到主页方法
    function jumpIframe(src){
    	$("#plc-iframe").attr('src','<%=path %>/'+src);
    }
    
	//主页提示操作成功或者失败方法
	function bcsPromptInfo(vals){
		var _html = '<div id="bcsAlert">';
			_html += '<div>'+vals+'!</div>';
			_html += '</div>';
		if($("#bcsAlert")){$("#bcsAlert").remove()};
		$("body").append(_html);
		setTimeout(function(){
			$("#bcsAlert").remove();
		},2000);
	}
    
</script>

</body>

</html>
