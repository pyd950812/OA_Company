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
    
	<link rel="stylesheet" href="<%=path %>/assets/js/layui/css/layui.css" /><!-- media="all"  -->
	
    <script type="text/javascript" src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/jquery/jquery.pagination.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>
	
    <script type="text/javascript" src="<%=path %>/assets/js/layer/laydate.js"></script>
	
    <script src="<%=path %>/assets/js/user_common.js"></script>
    
	<script type="text/javascript" src="<%=path %>/assets/js/layui/layui.js"></script>
	<script type="text/javascript" src="<%=path %>/assets/js/layui/layui-xtree.js"></script>
	
    <style type="text/css">
		.amap-sug-result{
			z-index:100000;
		}
		
		.isDisClaI,.isDisClaL {
			display:inline-block;
			float:left;
			margin-left: 10px;
			margin-right: 30px;
		}
		.isDisClaL {
			margin-top: -2px;
		}
		
		#fieldHidden{
			display: none;
		}
	</style>
	
	<script type="text/javascript">
		var olddata = JSON.parse('${olddata}');
		
		/* var permValue = '${permValue}';

		var permValueArray = permValue.split(","); */
	</script>

</head>
   
<body>
<div class="container-fluid GL-hzs">
	<!--头部内容-->
    <div class="header">
        <ol class="breadcrumb">
            <li>人事信息管理</li>
            <li>></li>
            <li class="active">部门信息修改</li>
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
        <div class="panel-body pad-tb-25" id="jcxx">
        	 <div class="row">
			 
             <!-- 以下为隐藏字段值 -->
             <div id="fieldHidden">
            
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 主键：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <%-- <input type="text" class="col-xs-12 GL-add-require" id="id" value="<%=request.getParameter("id") %>"> --%>
                        <input type="text" class="col-xs-12 GL-add-require" id="id" value="">
                    </div>
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
                    <span class="col-xs-3 glyphicon">* 是否可分配
                    </span>
                    <div class="col-xs-9 pad-0 row" id="isDis">
                        <!-- <input type="text" class="col-xs-12 GL-add-require" id="isDis" value=""> -->
                        
                        <input type="radio" name="isDisName" id="isDisId" class="isDisClaI" value="1"/><label class="isDisClaL" for="isDisId">是</label>
                        
                        <input type="radio" name="isDisName" id="isDisNotId" class="isDisClaI" value="2"/><label class="isDisClaL" for="isDisNotId">否</label>
                    </div>
                </div>
        	 	
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 权限
                    </span>
                    <!-- <div class="col-xs-9 pad-0 row" id="permDivId">
                        
                    </div> -->
                    
					<div class="layui-form" style="width:80%;"><!--  -->
                    <!-- <label class="layui-form-label">分配权限：</label> -->
					<!--权限树xtree  -->
					<div class="layui-form-item">
					
	                    <div id="permDivId" style="width:250px;margin-left: 100px"><!-- class="col-xs-9 pad-0 row" -->
	                        
	                    </div>
                    
			      	</div>
                    </div>
			      	
                </div>
        	 	
                
        	 </div>
        </div>
	
	</div>
</div>
</body>

<script type="text/javascript">

$(function(){
	/* 获取到权限信息 */
	<%-- $.ajax({
		url:'<%=path %>/permission/ajaxSelectPermListByUse',
   		type:'post',
       	success:function(data){
       		var permDataList = data.data.data;
       		var htmlStr = "";

       		for (var int = 0; int < permDataList.length; int++) {
       			var permData = permDataList[int];

       			if (isInArray(permValueArray,permData.id)) {
   					htmlStr+="<input type='checkbox' name='permName' value="+permData.id+" checked>"+permData.name+"";
				} else {
					htmlStr+="<input type='checkbox' name='permName' value="+permData.id+" >"+permData.name+"";
				}
       			
       			htmlStr+="<br/>";
			}
       		$("#permDivId").html(htmlStr);
       	}, 
       	error:function() {
       		alert("异常！");
       	}
    }); --%>


    var deptId = olddata.id;
    
    var xtree;
    layui.config({
    	base : "js/"
    }).use(['form','layer','jquery'],function(){
    	var form = layui.form,
    		
    	layer = parent.layer === undefined ? layui.layer : parent.layer,
   		laypage = layui.laypage;
    	
   		xtree = new layuiXtree({
   		      elem: 'permDivId'  //(必填) 放置xtree的容器id，不带#号
   		      , form: form    //(必填) layui 的 form
   		      , data: '<%=path %>/permission/ajaxSelectPermListByUseXtreeData?deptId='+deptId  //(必填) 数据接口，需要返回指定结构json字符串
   		      , ckall: true   //启动全选功能，默认false
   		      , ckallback: function () {}//全选框状态改变后执行的回调函数
   		      
   		      , isopen: true  //加载完毕后的展开状态，默认值：true
   		      
   		});
    })

    var permValue =[]; 

    function getPermValue(){ //jquery获取复选框值 
    	permValue =[]; 
    	
    	var list=xtree.GetChecked();
        
    	for(var i=0;i<list.length;i++){
    		var cheInVal = list[i].value;
    		permValue.push(cheInVal);
    		
    		if(xtree.GetParent(cheInVal)!=null){
    			var cheInVal1 = xtree.GetParent(cheInVal).value;
    			if (!isInArray(permValue, cheInVal1)) {
    				permValue.push(cheInVal1);
				}

    			if(xtree.GetParent(cheInVal1)!=null){
    				var cheInVal2 = xtree.GetParent(cheInVal1).value;
	    			if (!isInArray(permValue, cheInVal2)) {
	    				permValue.push(cheInVal2);
	    			}
    			}
    		}
    	}
        //alert(permValue);//1,2,3,4
    }

    var departmentParam = {};
    	departmentParam.id;
    	departmentParam.deptCode;
    	departmentParam.deptname;
    	departmentParam.deptinfo;
    	departmentParam.isDis;

    	$("#id").val(deptId);
    	$("#deptCode").val(olddata.deptCode);
    	$("#deptname").val(olddata.deptname);
    	$("#deptinfo").val(olddata.deptinfo);
    	
    	if (olddata.isDis == 1) {
    		$("#isDisId").attr("checked","checked");
    	} else {
    		$("#isDisNotId").attr("checked","checked");
    	}
    	
    	$("#save").click(function(){
    		var param = JSON.parse(JSON.stringify(departmentParam));
    		
    					param.id=$("#id").val();
    					param.deptCode=$("#deptCode").val();
    					param.deptname=$("#deptname").val();
    					param.deptinfo=$("#deptinfo").val();

    					var isDis = $("input[name='isDisName']:checked").val();

    					param.isDis=isDis;

    					getPermValue();
    					
    	    $.ajax({url:'<%=path %>/department/update',
           		type:'post',
           		cache:false,
           		dataType:'json',
           		data: {GridParam:JSON.stringify(param),
           			permValue:permValue+""},
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

});

</script>
</html>