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
            <li><a>***</a></li>
            <li>></li>
            <li><a>***</a></li>
            <li>></li>
            <li class="active">**</li>
        </ol>
    </div>
    <!--提示必填项部分-->
	<div class="filter panel panel-default">
		<div class="panel-heading" style="border-bottom:0px;">
			<span>温馨提示：带*的为必填部分，请核对完成后点击添加</span>
			
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
                    <span class="col-xs-3 glyphicon">* 主键：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="id" value="">
                    </div>
                </div>
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 部门编码：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="deptCode" value="">
                    </div>
                </div>
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 部门名称：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="deptname" value="">
                    </div>
                </div>
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 部门信息：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="deptinfo" value="">
                    </div>
                </div>
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 创建时间：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="createTime" value="">
                    </div>
                </div>
        	 	
                
        	 </div>
        </div>
	
	</div>
</div>
</body>

<script type="text/javascript">
var departmentParam = {};
	departmentParam.id;
	departmentParam.deptCode;
	departmentParam.deptname;
	departmentParam.deptinfo;
	departmentParam.createTime;

	$("#id").val(olddata.id);
	$("#deptCode").val(olddata.deptCode);
	$("#deptname").val(olddata.deptname);
	$("#deptinfo").val(olddata.deptinfo);
	$("#createTime").val(olddata.createTime);

	$("#save").click(function(){
		var param = JSON.parse(JSON.stringify(departmentParam));
		
					param.id=$("#id").val();
					param.deptCode=$("#deptCode").val();
					param.deptname=$("#deptname").val();
					param.deptinfo=$("#deptinfo").val();
					param.createTime=$("#createTime").val();
				
	    $.ajax({url:'<%=path %>/department/update',
       		type:'post',
       		cache:false,
       		dataType:'json',
       		data: JSON.stringify(param),
       		contentType: "application/json;charset=UTF-8",
           	success:function(data){
           		if(data.code == "OK"){
           			alert("数据修改成功");
               		window.location.href= "<%=path %>/department/show";
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
		window.location.href= "<%=path %>/department/show";
	});

</script>
</html>