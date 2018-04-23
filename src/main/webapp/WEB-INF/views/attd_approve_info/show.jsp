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
    
	<style type="text/css">
		span.glyphicon{
			height:30px;
			line-height:30px;
		}
		
	</style>
	
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
            <li class="active">考勤审批提交管理</li>
	        <button class="chaxun-bottom" id="attd_approve_info_AskForLeave">请假</button>
	        <button class="chaxun-bottom" id="attd_approve_info_ExchangeHoliday">调休</button>
	        <button class="chaxun-bottom" id="attd_approve_info_OnBusiness">出差</button>
        </ol>
    </div>
    <!--过滤条件-->
    <div class="filter panel panel-default">
        <div class="panel-heading">
            <span class="glyphicon glyphicon-search"></span>
            <span>过滤条件</span>
        </div>
        <div class="panel-body pad-tb-25">
            <span>用户名：</span>
	        <input type="text" placeholder="请输入用户名" id="searchSelectRealname">
            <span>请假日期：</span>
	        <input type="text" placeholder="请输入日期" id="searchSelectCreateTime">
	        <button class="chaxun-bottom" id="attd_approve_info_chaxun">查询</button>
        </div>
        
        <div class="panel panel-default">
        	<div class="panel-heading">
            <span class="iconstate left bg-filter"></span>
            <span class="left bg-filter">考勤审批提交数据表</span>
			<!-- <button class="tianjia-button right bg-filter" id="attd_approve_info_remove"><span class="glyphicon glyphicon-remove"></span> 删除</button>
			<button class="tianjia-button right bg-filter" id="attd_approve_info_edit"><span class="glyphicon glyphicon-edit"></span> 修改</button>
            <button class="tianjia-button right bg-filter" id="attd_approve_info_plus"><span class="glyphicon glyphicon-th-list"></span> 查看审核记录</button> -->
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
	var attd_approve_infoParam = {};
	attd_approve_infoParam.id;
	attd_approve_infoParam.empId;
	attd_approve_infoParam.approveDays;
	attd_approve_infoParam.approveContent;
	attd_approve_infoParam.approveRemark;
	attd_approve_infoParam.approveType;
	attd_approve_infoParam.approveTimeStart;
	attd_approve_infoParam.approveTimeEnd;
	attd_approve_infoParam.createTime;
	attd_approve_infoParam.approveState;
	attd_approve_infoParam.updateTime;

	$(function(){
		var GridParam = JSON.parse(JSON.stringify(attd_approve_infoParam));
		$("#GRIDTABLE").jqGrid({
            //caption:'权限管理',
            url: '<%=path %>/attd_approve_info/selectRelationData',
            styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式
            datatype: "json", //数据类型
            mtype: "post",//提交方式
            postData: {GridParam: JSON.stringify(GridParam)},
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
				//"",
			"申请人",
			"天数",
			"原因",
			"备注",
			"类型",// 1-请假申请 2-调休申请 3-出差申请
			"开始日期",
			"结束日期",
			"审批状态",
			"修改时间",
			"操作"
			],
            colModel: [
				/* {name: "id", index: "id", sortable: false, width: 60, align: "center", hidden:true}, */
			{name: "realname", index: "realname", sortable: false, width: 60, align: "center"},
			{name: "approve_days", index: "approve_days", sortable: false, width: 60, align: "center"},
			{name: "approve_content", index: "approve_content", sortable: false, width: 60, align: "center"},
			{name: "approve_remark", index: "approve_remark", sortable: false, width: 60, align: "center"},
			{name: "approve_type", index: "approve_type", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				var approveTypeStr = "";
				switch(value){//1-请假申请 2-调休申请 3-出差申请
				    case 0:approveTypeStr = "未知状态";break;  
				    case 1:approveTypeStr = "请假";break;  
				    case 2:approveTypeStr = "调休";break;  
				    case 3:approveTypeStr = "出差";break;
				    default:approveTypeStr = "未知状态";break;
				}
				return approveTypeStr;
			}},
			{name: "approve_time_start", index: "approve_time_start", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				return getFormatDate(value);
			}},
			{name: "approve_time_end", index: "approve_time_end", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				return getFormatDate(value);
			}},
			{name: "approve_state", index: "approve_state", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				var approveStateStr = "";
				switch(value){//1-请假申请 2-调休申请 3-出差申请
			    	case 0:approveStateStr = "等待提交";break;  
				    case 1:approveStateStr = "开始审批";break;  
				    case 2:approveStateStr = "审批中";break;  
				    case 3:approveStateStr = "审批通过";break;  
				    case 4:approveStateStr = "审批驳回";break;
				    default:approveStateStr = "未知状态";break;
				}
				return approveStateStr;
			}},
			{name: "update_time", index: "update_time", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
				return getFormatDate(value);
			}},
			{name: "id", index: "id", sortable: false, width: 120, align: "center",formatter:function(value,options,rowData){
				//var approveState = $("#GRIDPAGE").getCell(this, "approve_state");
				var approveState = rowData.approve_state;
				
				var actHtml = "";
				if (approveState == 0) {//0-等待提交 1-开始审批 2-审批中 3-审批通过 4-审批驳回
					actHtml += "<a href='#' onclick='javascript:delAttdApprove("+value+");return false;'>删除</a>";
					actHtml += "&nbsp;&nbsp;&nbsp;";
					actHtml += "<a href='#' onclick='javascript:updateAttdApprove("+value+");return false;'>修改</a>";
					actHtml += "&nbsp;&nbsp;&nbsp;";
					actHtml += "<a href='#' onclick='javascript:commitAttdApprove("+value+","+rowData.approve_type+");return false;'>提交申请</a>";
				} else if (approveState == 1) {//0-等待提交 1-开始审批 2-审批中 3-审批通过 4-审批驳回
					actHtml += "<a href='#' onclick='javascript:delAttdApprove("+value+");return false;'>删除</a>";
					actHtml += "&nbsp;&nbsp;&nbsp;";
					actHtml += "<a href='#' onclick='javascript:viewAttdApproveHisComment("+value+");return false;'>查看批注记录</a>";
				} else if (approveState == 2) {//0-等待提交 1-开始审批 2-审批中 3-审批通过 4-审批驳回
					actHtml += "<a href='#' onclick='javascript:delAttdApprove("+value+");return false;'>删除</a>";
					actHtml += "&nbsp;&nbsp;&nbsp;";
					actHtml += "<a href='#' onclick='javascript:viewAttdApproveHisComment("+value+");return false;'>查看批注记录</a>";
				} else if (approveState == 3 || approveState == 4) {
					actHtml += "<a href='#' onclick='javascript:viewAttdApproveHisComment("+value+");return false;'>查看批注记录</a>";
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
            pager: "#GRIDPAGE"
        });
        $("#GRIDPAGE").css("height", "45px");
	});

	//0-等待提交 1-开始审批 2-审批中 3-审批通过 4-审批驳回
	
	function delAttdApprove(attdApproveId){//审批结束之前【approveState == 0 || 1 || 2】可以删除审批以及关联任务项
		if (confirm("确认删除当前审批吗？")) {
			$.ajax({url:'<%=path %>/attd_approve_info/deleteById',
	       		type:'post',
	       		cache:false,
	       		dataType:'json',
	       		data:{id: attdApproveId},
	           	success:function(data){
	           		if(data.code == "OK"){
	           			alert("数据删除成功");
	           			window.location.href= "<%=path %>/attd_approve_info/show";
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

	function updateAttdApprove(attdApproveId){//未进入审批流之前【approveState == 0】可以更改 - 【不在审批任务流内【approveState == 0 || 3 || 4】可以更改】
		window.location.href= "<%=path %>/attd_approve_info/edit?id="+attdApproveId;
	}

	function commitAttdApprove(attdApproveId, attdApproveType){//未进入审批流之前【approveState == 0】提交
		//alert(attdApproveId);
		//alert(attdApproveType);
		if (confirm("是否确认提交审批？")) {
			$.ajax({url:'<%=path %>/activiti_flow/commitAttdApprove',
	       		type:'post',
	       		data:{
	       			attdApproveId:attdApproveId,
	       			attdApproveType:attdApproveType
	       		},
                dataType:'json',
	           	success:function(data){
	           		if(data.code == "OK"){
	           			alert("审批提交成功");
	           			window.location.href= "<%=path %>/attd_approve_info/show";
	           		} else {
	           			alert(data.msg);
	           		}
	           	}, 
	           	error : function(data,type, err) {
	           		alert(type);
	           		alert(err);
	           	}
	        });
		}
	}

	function viewAttdApproveHisComment(attdApproveInfoId){//审批流操作以后【approveState == 1 || 2 || 3 || 4】查看审核记录
		//window.location.href= "<%=path %>/attd_approve_list/show";
		window.location.href= "<%=path %>/attd_approve_list/show?aAInfoId="+attdApproveInfoId;
	}

    //新增
	$("#attd_approve_info_AskForLeave").click(function(){//请假 - approve_type-1
		window.location.href= "<%=path %>/attd_approve_info/add?approve_type=1";
	});

	$("#attd_approve_info_ExchangeHoliday").click(function(){//调休 - approve_type-2
		window.location.href= "<%=path %>/attd_approve_info/add?approve_type=2";
	});

	$("#attd_approve_info_OnBusiness").click(function(){//出差 - approve_type-3
		window.location.href= "<%=path %>/attd_approve_info/add?approve_type=3";
	});

	var searchGridParam = JSON.stringify(attd_approve_infoParam);
	
    //查询
	$("#attd_approve_info_chaxun").click(function(){
		var param = JSON.parse(searchGridParam);
		
		//为param 赋值
		var GridParam = JSON.stringify(param);
		searchFun(GridParam);
	});

	function searchFun(GridParam){
		$("#GRIDTABLE").jqGrid("setGridParam",{
			url:"<%=path %>/attd_approve_info/select",
			postData:{GridParam:GridParam},
			page:1
		}).trigger("reloadGrid");
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