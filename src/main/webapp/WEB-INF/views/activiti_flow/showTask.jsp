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
            <li class="active">任务管理</li>
        </ol>
    </div>
    <!--过滤条件-->
    <div class="filter panel panel-default">
        
        <div class="panel panel-default">
        	<div class="panel-heading">
            <span class="iconstate left bg-filter"></span>
            <span class="left bg-filter">任务数据表</span>
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
            url: '<%=path %>/activiti_flow/selectEmpTastList',
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
				"任务ID",
				"任务名称",
				"经办人",
				"创建时间",
				"查看",
				"办理"
			],
            colModel: [
				{name: "id", index: "id", sortable: false, width: 60, align: "center", hidden:true},
			{name: "name", index: "name", sortable: false, width: 60, align: "center"},
			{name: "assignee", index: "assignee", sortable: false, width: 60, align: "center"},
			{name: "createTime", index: "createTime", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				return getFormatDate(value);
			}},
			{name: "viewCurrent", index: "viewCurrent", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				return "<a target='_blank' href='<%=path %>/activiti_flow/viewCurrentImage?taskId="+rowData.id+"'>查看当前流程图</a>";
			}},
			{name: "handle", index: "handle", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				var taskName = rowData.name;
				
				var actHtml = "";

				if ("完成任务【部门职员】" == taskName) {
					actHtml = "<a href='#' onclick='javascript:finishTask("+rowData.id+");return false;'>完成任务</a>";
				} else {
					actHtml = "<a href='#' onclick='javascript:handleTask("+rowData.id+");return false;'>办理</a>";
				}
				return actHtml;
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
	
	function handleTask(value){
		//alert(value);
		window.location.href= "<%=path %>/activiti_flow/handleTask?taskId="+value;
	}
	
	function finishTask(value){
		window.location.href= "<%=path %>/activiti_flow/finishTask?taskId="+value;
	}
	
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