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
            <li><a>合同信息管理</a></li>
            <li>></li>
            <li class="active">合同信息修改</li>
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
			 
             <!-- 以下为隐藏字段值 -->
             <div id="fieldHidden">
            
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 自增id：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <%-- <input type="text" class="col-xs-12 GL-add-require" id="id" value="<%=request.getParameter("id") %>"> --%>
                        <input type="text" class="col-xs-12 GL-add-require" id="id" name="idName" value="">
                    </div>
                </div>
                
             </div>
                
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 所属员工：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <select id="empId" name="empIdName" style="width:200px;" >
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
                
                
        	 </div>
        </div>
	
</form>
        
	</div>
</div>
</body>

<script type="text/javascript">
var contractParam = {};
	contractParam.id;
	contractParam.empId;

	$("#id").val(olddata.id);

	//员工姓名的回显
	$.ajax({
		url:'<%=path %>/employee/ajaxSelectEmpById',
   		type:'post',
   		cache:false,
   		dataType:'json',
   		data: {"empId":olddata.empId},
       	success:function(data){
       		var list = data.data.data;
		    $("#empId").html("<option value='"+olddata.empId+"'>"+list+"</option>");
       	}, 
       	error:function() {
       		alert("异常！");
       	}
    });
$("#save").click(function () {
    var formData = new FormData($('#uploadForm')[0]);

    $.ajax({
        url:'<%=path %>/contract/update',
        type:'post',
        data: formData,
        cache:false,
        data: formData,
        //JSON.stringify(param),
        processData: false,
        contentType: false,
       success:function(data) {
                if(data.code == "OK"){
                    alert("修改数据成功！");
                    window.location.href="<%=path %>/contract/show";
                }else{
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