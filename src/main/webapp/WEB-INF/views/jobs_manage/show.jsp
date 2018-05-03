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
            <li class="active">任务管理</li>

            <button class="chaxun-bottom" id="jobs_manage_PublishTask">发布任务</button>

        </ol>
    </div>
    <!--过滤条件-->
    <div class="filter panel panel-default">
        <div class="panel-heading">
            <span class="glyphicon glyphicon-search"></span>
            <span>过滤条件</span>
        </div>
        <div class="panel-body pad-tb-25">
            <span>员工名称：</span>
            <input type="text" placeholder="请输入员工名称" id="searchSelectRealname">
            <button class="chaxun-bottom" id="jobs_manage_chaxun">查询</button>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">
                <span class="iconstate left bg-filter"></span>
                <span class="left bg-filter">任务分配表</span>
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
    var jobs_manageParam = {};
    jobs_manageParam.id;
    jobs_manageParam.createUserId;
    jobs_manageParam.workUserId;
    jobs_manageParam.jobInfo;
    jobs_manageParam.jobState;
    jobs_manageParam.createTime;
    jobs_manageParam.updateTime;
    jobs_manageParam.jobWorkInfo;

    $(function(){
        var GridParam = JSON.parse(JSON.stringify(jobs_manageParam));
        $("#GRIDTABLE").jqGrid({
            //caption:'权限管理',
            url: '<%=path %>/jobs_manage/selectRelationData',
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
                /* "", */
                /* "创建领导", */
                "",
                "指定员工",
                "工作描述",
                "工作状态",
                /* "创建时间", */
                "修改时间",
                /* "工作小结", */
                "操作"
            ],
            colModel: [
                /* {name: "id", index: "id", sortable: false, width: 60, align: "center", hidden:true}, */
                /* {name: "realname1", index: "realname1", sortable: false, width: 60, align: "center"}, */
                {name: "work_user_id", index: "work_user_id", sortable: false, width: 60, align: "center", hidden:true},
                {name: "realname2", index: "realname2", sortable: false, width: 60, align: "center"},
                {name: "job_info", index: "jobInfo", sortable: false, width: 60, align: "center"},
                {name: "job_state", index: "job_state", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
                    var jobStateStr = "";
                    switch(value){// 0-新建 1-进行中 2-已解决 3-已关闭 4-已驳回
                        case 0:jobStateStr = "新建";break;
                        case 1:jobStateStr = "进行中";break;
                        case 2:jobStateStr = "已解决";break;
                        /* case 3:jobStateStr = "已关闭";break; */
                        default:jobStateStr = "未知状态";break;
                    }
                    return jobStateStr;
                }},
                /* {name: "createTime", index: "createTime", sortable: false, width: 60, align: "center"}, */
                {name: "update_time", index: "update_time", sortable: false, width: 60, align: "center",formatter:function(value,options,rowData){
                    return getFormatDate(value);
                }},
                /* {name: "job_work_info", index: "job_work_info", sortable: false, width: 60, align: "center"}, */
                {name: "id", index: "id", sortable: false, width: 120, align: "center",formatter:function(value,options,rowData){
                    var jobState = rowData.job_state;

                    var actHtml = "";
                    if (jobState == 0) {// 0-新建 1-进行中 2-已解决 3-已关闭 4-已驳回
                        actHtml += "<a href='#' onclick='javascript:delJobsManage("+value+");return false;'>删除</a>";
                        actHtml += "&nbsp;&nbsp;&nbsp;";
                        actHtml += "<a href='#' onclick='javascript:updateJobsManage("+value+");return false;'>修改</a>";
                        actHtml += "&nbsp;&nbsp;&nbsp;";
                        actHtml += "<a href='#' onclick='javascript:commitJobsManage("+value+","+rowData.work_user_id+");return false;'>分配</a>";
                    } else if (jobState == 1) {
                        actHtml += "<a href='#' onclick='javascript:delJobsManage("+value+");return false;'>删除</a>";
                        /* actHtml += "&nbsp;&nbsp;&nbsp;";
                        actHtml += "<a href='#' onclick='javascript:viewJobsManageHisComment("+value+");return false;'>查看批注记录</a>"; */
                    } else if (jobState == 2) {
                        actHtml += "<a href='#' onclick='javascript:viewJobsManage("+value+");return false;'>查看工作小结</a>";
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

    function delJobsManage(jobsManageId, workUserId){
        if (confirm("确认删除当前审批吗？")) {
            $.ajax({url:'<%=path %>/jobs_manage/deleteById',
                type:'post',
                cache:false,
                dataType:'json',
                data:{id: jobsManageId},
                success:function(data){
                    if(data.code == "OK"){
                        alert("数据删除成功");
                        window.location.href= "<%=path %>/jobs_manage/show";
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

    function commitJobsManage(jobsManageId, workUserId){//
        if (confirm("是否确认分配？")) {
            $.ajax({url:'<%=path %>/activiti_flow/commitJobsManage',
                type:'post',
                data:{
                    jobsManageId:jobsManageId,
                    workUserId:workUserId
                },
                success:function(data){
                    if(data.code == "OK"){
                        alert("任务分配成功");
                        window.location.href= "<%=path %>/jobs_manage/show";
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

    function viewJobsManage(jobsManageId){
        $.ajax({url:'<%=path %>/jobs_manage/viewJobWorkInfo',
            type:'post',
            data:{
                jobsManageId:jobsManageId
            },
            dataType: "json",
            success:function(data){
                if(data.code == "OK"){
                    alert(data.data.data[0].jobWorkInfo);
                } else {
                    alert(data.msg);
                }
            },
            error : function() {
                alert("异常！");
            }
        });
    }

    function updateJobsManage(jobsManageId){
        window.location.href= "<%=path %>/jobs_manage/edit?id="+jobsManageId;
    }

    var searchGridParam = JSON.stringify(jobs_manageParam);

    //查询
    $("#jobs_manage_chaxun").click(function(){
        var param = JSON.parse(searchGridParam);

        //为param 赋值
        var GridParam = JSON.stringify(param);

        var empRealname = $("#searchSelectRealname").val();

        searchFun(GridParam, empRealname);
    });

    function searchFun(GridParam, empRealname){
        $("#GRIDTABLE").jqGrid("setGridParam",{
            url:"<%=path %>/jobs_manage/selectRelationDataByEmpRealname",
            postData:{GridParam:GridParam,
                empRealname:empRealname},
            page:1
        }).trigger("reloadGrid");
    }

    //新增
    $("#jobs_manage_PublishTask").click(function(){
        window.location.href= "<%=path %>/jobs_manage/add";
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