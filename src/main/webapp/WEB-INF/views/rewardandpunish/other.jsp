<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>公司OA系统</title>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8">

    <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="<%=path %>/assets/css/iframe.css">

    <link rel="stylesheet" href="<%=path %>/assets/css/ui.jqgrid.css">

    <script src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script src="<%=path %>/assets/js/jquery/grid.locale-cn.js"></script>
    <script src="<%=path %>/assets/js/jquery/jquery.jqGrid.min.js"></script>
    <script src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>

    <script src="<%=path %>/assets/js/user_common.js"></script>
</head>

<body>
<div class="container-fluid GL-hzs">
    <!--头部内容-->
    <div class="header">
        <ol class="breadcrumb">
            <li><a>首页</a></li>
            <li>></li>
            <li>薪资管理</li>
            <li>></li>
            <li class="active">员工奖罚管理</li>
        </ol>
    </div>
    <!--过滤条件-->
    <div class="filter panel panel-default">
        <%--<div class="panel-heading">--%>
            <%--<span class="glyphicon glyphicon-search"></span>--%>
            <%--<span>查询</span>--%>
        <%--</div>--%>
        <%--<div class="panel-body pad-tb-25">--%>
            <%--<span>员工姓名：</span>--%>
            <%--<input type="text" placeholder="请输入员工姓名" id="searchSelectEmpName">--%>
            <%--<button class="chaxun-bottom" id="reward_chaxun">查询</button>--%>
        <%--</div>--%>


    <div class="filter panel panel-default">
        <div class="panel-heading">
            <span>奖罚信息</span>
            <c:if test="${sessionScope.name == 'admin' }">
                    <button class="tianjia-button right bg-filter" id ="reward_add"><span class="glyphicon glyphicon-plus"></span> 添加</button>
                    <button class="tianjia-button right bg-filter" id ="reward_edit"><span class="glyphicon glyphicon-edit"></span> 修改</button>
                    <button class="tianjia-button right bg-filter" id ="reward_remove"><span class="glyphicon glyphicon-remove"></span> 删除</button>
            </c:if>
        </div>

        <%-- 表格 --%>
        <div class="panel-body">
            <table id="GRIDTABLE" style="border-collapse: collapse"></table>
            <div id="GRIDPAGE"></div>
        </div>

    </div>
</div>
<script>
    var rewardParam = {};
    rewardParam.id;
    rewardParam.empId;
    rewardParam.empName;
    rewardParam.rewardState;
    rewardParam.rewardTime;
    rewardParam.rewardMoney;
    rewardParam.rewardReason;
    rewardParam.createTime;
    rewardParam.updateTime;

    $(function(){
        var GridParam = JSON.parse(JSON.stringify(rewardParam));
        $("#GRIDTABLE").jqGrid({
            //caption:'权限管理',
            url: "<%=path %>/employeeReward/otherSelect", //请求路径
            styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式
            datatype: "json", //从服务器端返回的数据类型
            mtype: "post",//ajax提交方式
            postData: {GridParam: JSON.stringify(GridParam)}, //此数组内容直接赋值到url上
            //width: $(".jqGrid_wrapper").css("width"),,
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
                "",
                "员工编码",
                "员工姓名",
                "1奖or0罚",
                "奖罚日期",
                "奖罚金额",
                "奖罚理由",
                "创建时间",
                "修改时间"
            ],
            colModel: [
                {name: "id", index: "id", sortable: false, width: 60, align: "center", hidden:true},
                {name: "empId", index: "empId", sortable: false, width: 60, align: "center"},
                {name: "empName", index: "empName", sortable: false, width: 60, align: "center"},
                {name: "rewardState", index: "rewardState", sortable: false, width: 60, align: "center"},
                {name: "rewardTime", index: "rewardTime", sortable: false, width: 60, align: "center"},
                {name: "rewardMoney", index: "rewardMoney", sortable: false, width: 60, align: "center"},
                {name: "rewardReason", index: "rewardReason", sortable: false, width: 60, align: "center"},
                {name: "createTime", index: "createTime", sortable: false, width: 60, align: "center"},
                {name: "updateTime", index: "updateTime", sortable: false, width: 60, align: "center"}
            ],
            viewrecords: true, //是否在浏览导航栏显示记录总数
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

</script>
</div>
</body>