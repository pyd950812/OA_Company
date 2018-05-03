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
            <li class="active">审批任务管理</li>
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
                    <span class="col-xs-3 glyphicon">* 天数：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="number" class="col-xs-12 GL-add-require" id="approveDays" value="" disabled="disabled">
                    </div>
                </div>
                
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 原因：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <input type="text" class="col-xs-12 GL-add-require" id="approveContent" value="" disabled="disabled">
                    </div>
                </div>
                
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 备注：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <textarea class="col-xs-12 GL-add-require" id="approveRemark" value="" disabled="disabled"></textarea>
                    </div>
                </div>
                
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 批注：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <textarea class="col-xs-12 GL-add-require" id="annotation" value=""></textarea>
                    </div>
                </div>
                
        	 </div>
        </div>
	
	</div>
	
	<div class="filter panel panel-default">
		
        <div class="panel panel-default">
        	<div class="panel-heading">
            <span class="iconstate left bg-filter"></span>
            <span class="left bg-filter">历史考勤审批批注</span>
			</div>
        </div>
        <div class="panel-body">
            <table id="GRIDTABLE" style="border-collapse: collapse"></table>
            <div id="GRIDPAGE"></div>
        </div>
        
	</div>
	
</div>
<script>

$(function(){
	var outcomeListSize = '${outcomeListSize}';
	
	var actHtmlStr = "<span>温馨提示：带*的为必填部分，请核对完成后点击保存</span>";
	if (outcomeListSize != null && outcomeListSize>0) {
		actHtmlStr += "<span type = 'button' id ='save' class='save'>保存</span>";
	}
	actHtmlStr += "<span type = 'button' id ='back' class='back'>返回</span>";
	
	$("#spanDivId").html(actHtmlStr);
	
	var attdApproveInfo = JSON.parse('${attdApproveInfo}');
	//var attdApproveInfo = '${attdApproveInfo}';
	
	$("#approveDays").val(attdApproveInfo.approveDays);
	$("#approveContent").val(attdApproveInfo.approveContent);
	$("#approveRemark").val(attdApproveInfo.approveRemark);

	var taskId = '${taskId}';
	
	$("#save").click(function(){
		var annotation = $("#annotation").val();
		//alert(annotation);

	    $.ajax({url:'<%=path %>/attd_approve_list/insert',
       		type:'post',
            data: {annotation:annotation,
            	attdApproveInfoId:attdApproveInfo.id,
            	taskId:taskId}, 
           	success:function(data){
           		if(data.code == "OK"){
           			alert("任务办理成功");
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

	$("#GRIDTABLE").jqGrid({
        //caption:'权限管理',
        url: '<%=path %>/attd_approve_list/selectHisRelationData',
        styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式
        datatype: "json", //数据类型
        mtype: "post",//提交方式
        postData: {taskId:taskId},
        //width: $(".jqGrid_wrapper").css("width"),
        autowidth: true,//自动宽
        //shrinkToFit: true,
        height: '70%',//高度，表格高度。可为数值、百分比或'auto'
        sortorder: 'asc',
        viewrecords: true,//是否在浏览导航栏显示记录总数
        altRows: true,//设置为交替行表格,默认为false
        //rownumbers : true,//是否显示行号
        //rownumWidth : '80px', //设置行号的宽度
        //multiselect: true,//定义多选选择框
        //multiboxonly : true,//单选框
        colNames: [
			"",
		"批注用户",
		"批注内容",
		"批注日期"
		,"关联的审批申请表的ID"
		],
        colModel: [
			{name: "id", index: "id", sortable: false, width: 60, align: "center", hidden:true},
		{name: "realname", index: "realname", sortable: false, width: 60, align: "center"},
		{name: "annotation", index: "annotation", sortable: false, width: 60, align: "center"},
		{name: "create_time", index: "create_time", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
			return getFormatDate(value);
		}}
		,{name: "aAInfoId", index: "aAInfoId", sortable: false, width: 60, align: "center", hidden:true}
        ],
        rowNum:150, 
		rowList:[15,30,50], 
		//loadonce: true, 
		jsonReader : {  
			root:"root", //结果集
			page: "page", //第几页
			total: "total", //总页数
			records: "records", //数据总数
			repeatitems: false
			}
        //,pager: "#GRIDPAGE"
    });
    $("#GRIDPAGE").css("height", "45px");
	
});

</script>
</body>