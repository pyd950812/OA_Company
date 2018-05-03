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
<div class="container-fluid GL-hzs">
    <!--头部内容-->
    <div class="header">
        <ol class="breadcrumb">
            <li>工作管理</li>
            <li>></li>
            <li class="active">业务流程部署</li>
        </ol>
    </div>
    <!--过滤条件-->
    <div class="filter panel panel-default">
        <!-- <div class="panel-heading">
            <span class="glyphicon glyphicon-search"></span>
            <span>过滤条件</span>
        </div>
        <div class="panel-body pad-tb-25">
            <span>流程名称：</span>
	        <input type="text" placeholder="请输入流程名称" id="searchSelectActivitiFlowName">
	        <button class="chaxun-bottom" id="activiti_flow_chaxun">查询</button>
        </div> -->
        
        <div class="panel panel-default">
        	<div class="panel-heading">
            <span class="iconstate left bg-filter"></span>
            <span class="left bg-filter">部署统计</span>
            
            <button class="tianjia-button right bg-filter" id="activiti_flow_plus"><span class="glyphicon glyphicon-plus"></span>添加</button>
			
        	</div>
        </div>
        <div class="panel-body">
            <table id="GRIDTABLE" style="border-collapse: collapse"></table>
            <div id="GRIDPAGE"></div>
        </div>
    </div>
</div>   
</body>

<script type="text/javascript">

	$(function(){
		$("#GRIDTABLE").jqGrid({
            //caption:'权限管理',
            url: '<%=path %>/activiti_flow/selectAllDeployment',
            styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式
            datatype: "json", //数据类型
            mtype: "post",//提交方式
            postData: {},
            //width: $(".jqGrid_wrapper").css("width"),
            autowidth: true,//自动宽
            //shrinkToFit: true,
            height: '70%',//高度，表格高度。可为数值、百分比或'auto'
            sortorder: 'asc',
            viewrecords: true,//是否在浏览导航栏显示记录总数
            altRows: true,//设置为交替行表格,默认为false
            //rownumbers : true,//是否显示行号
            //rownumWidth : '80px', //设置行号的宽度
            multiselect: true,//定义多选选择框
            multiboxonly : true,//单选框
            colNames: [
				/* "部署ID", */
				"流程名称",
				"发布日期",
				"查看",
				"删除"
			],
            colModel: [
				/* {name: "id", index: "id", sortable: false, width: 60, align: "center", hidden:true}, */
			{name: "name", index: "name", sortable: false, width: 60, align: "center"},
			{name: "deploymentTime", index: "deploymentTime", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				//alert(value);
				return getFormatDate(value);
			}},
			{name: "pdid", index: "pdid", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				return "<a target='_blank' href='<%=path %>/activiti_flow/viewImage?pdid="+value+"'>查看流程图</a>";
			}},
			{name: "id", index: "id", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				<%-- return "<a href='<%=path %>/activiti_flow/delDeployment?depid="+value+"'>删除</a>"; --%>
				return "<a href='#' onclick='javascript:delDeployment("+value+");return false;'>删除</a>";
			}}
            ],
            rowNum:15, 
    		rowList:[15,30,50], 
    		//loadonce: true, 
    		jsonReader : {  
    			root:"root", //结果集
    			page: "page", //第几页
    			total: "total", //总页数
    			records: "records", //数据总数
    			repeatitems: false
   			},
   			loadError: function(xhr,status,error){  
 			    alert(status + " loading data of " + $(this).attr("id") + " : " + error );    
   			},
            pager: "#GRIDPAGE"
        });
        $("#GRIDPAGE").css("height", "45px");
	});
	
	function delDeployment(value){
		//alert(value);
		if (confirm("是否确认删除该部署项？")) {
			$.ajax({url:'<%=path %>/activiti_flow/delDeployment',
	       		type:'post',
	       		//dataType:'json',
	       		data:{
	       			depid:value+""
	       		},
	       		/* cache:false,
	       		contentType: "application/json;charset=UTF-8", */
	           	success:function(data){
	           		if(data.code == "OK"){
	           			alert("数据删除成功");
	           			window.location.href= "<%=path %>/activiti_flow/show";
	           		} else {
	           			alert(data.msg);
	           		}
	           	}, 
	           	error : function() {
	           		alert("异常！");
	           	}
	        });
		}
	}
	
    //查询
	$("#attendance_chaxun").click(function(){
		var param = JSON.parse(searchGridParam);

		var empRealname = $("#searchSelectRealname").val();

		var selectCreateTime = $("#searchSelectCreateTime").val();
		param.createTime = selectCreateTime+ " 00:00:00";
		
		//为param 赋值
		var GridParam = JSON.stringify(param);
		searchFun(GridParam, empRealname);
	});

	function searchFun(GridParam, empRealname){
		$("#GRIDTABLE").jqGrid("setGridParam",{
			url:"<%=path %>/attendance/selectRelationDataByEmpRealname",
			postData:{GridParam:GridParam, empRealname:empRealname},
			page:1
		}).trigger("reloadGrid");
	}

    //新增
	$("#activiti_flow_plus").click(function(){
		window.location.href= "<%=path %>/activiti_flow/add";
	});

    //表格自适应屏幕
    $(function(){
    	$(window).resize(function(){
    		$('#GRIDTABLE').setGridWidth($(window).width()*0.9);
    		$('#GRIDTABLE').setGridWidth(document.body.clientWidth*0.9);
    		$("GRIDTABLE").setGridHeight($(window).height()*0.9);
    		$("GRIDTABLE").setGridHeight($(window).height()*0.9);
    	})
    });
    
</script>
</html>