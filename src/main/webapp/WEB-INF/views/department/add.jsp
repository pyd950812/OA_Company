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

    <script type="text/javascript" src="<%=path %>/assets/js/layer/laydate.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/layui/layui.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/layui/layui-xtree.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/jquery/jquery.pagination.js"></script>
    <script type="text/javascript" src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>


	
    <script src="<%=path %>/assets/js/user_common.js"></script>
    
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
	
</head>
   
<body>
<div class="container-fluid GL-hzs">
	<!--头部内容-->
    <div class="header">
        <ol class="breadcrumb">
            <li>人事信息管理</li>
            <li>></li>
            <li class="active">部门信息新增</li>
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
                    <span class="col-xs-3 glyphicon">* 是否可分配：
                    </span>
                    <div class="col-xs-9 pad-0 row">
                        <!-- <input type="text" class="col-xs-12 GL-add-require" id="isDis" value=""> -->
                        
                        <input type="radio" name="isDisName" id="isDisId" class="isDisClaI" value="1" checked/><label class="isDisClaL" for="isDisId">是</label>
                        
                        <input type="radio" name="isDisName" id="isDisNotId" class="isDisClaI" value="2"/><label class="isDisClaL" for="isDisNotId">否</label>
                    </div>
                </div>
        	 	
				<div class="col-xs-6 row ie-col-6">
                    <span class="col-xs-3 glyphicon">* 权限：
                    </span>
                    
                    
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
       			htmlStr+="<input type='checkbox' name='permName' value="+permData.id+" checked>"+permData.name+"";
       			htmlStr+="<br/>";
			}
       		$("#permDivId").html(htmlStr);
       	}, 
       	error:function() {
       		alert("异常！");
       	}
    }); --%>

    
    //var xtreeJq;
    
    var xtree;
    layui.config({
    	base : "js/"
    }).use(['form','layer','jquery'],function(){
    	var form = layui.form,
    		
    	layer = parent.layer === undefined ? layui.layer : parent.layer,
   		laypage = layui.laypage;
    	
    	//xtreeJq = layui.jquery;
   		
   		xtree = new layuiXtree({
   		      elem: 'permDivId'  //(必填) 放置xtree的容器id，不带#号
   		      , form: form    //(必填) layui 的 form
   		      , data: '<%=path %>/permission/xtreedata'  //(必填) 数据接口，需要返回指定结构json字符串
   		      , ckall: true   //启动全选功能，默认false
   		      , ckallback: function () {}//全选框状态改变后执行的回调函数
   		      
   		      , isopen: true  //加载完毕后的展开状态，默认值：true
   		      
   		      /* , click: function (data) {  //节点选中状态改变事件监听，全选框有自己的监听事件
  		            alert(data.elem); //得到checkbox原始DOM对象
  		        	alert(data.elem.checked); //开关是否开启，true或者false
  		     		alert(data.value); //开关value值，也可以通过data.elem.value得到
  		  			alert(data.othis); //得到美化后的DOM对象
  		          } */
   		      
   		       /* , icon: {        //三种图标样式，更改几个都可以，用的是layui的图标
   		           open: "图标代号"       //节点打开的图标
   		           , close: "图标代号"    //节点关闭的图标
   		           , end: "图标代号"      //末尾节点的图标
   		       }
   		       , color: {       //三种图标颜色，独立配色，更改几个都可以
   		           open: "#EE9A00"        //节点图标打开的颜色
   		           , close: "#EEC591"     //节点图标关闭的颜色
   		           , end: "#828282"       //末级节点图标的颜色
   		       }
   		        */
   		});
    })

    var permValue =[]; 

    function getPermValue(){ //jquery获取复选框值 
    	permValue =[]; 
    	/* $('input[name="permName"]:checked').each(function(){ 
    		permValue.push($(this).val()); 
    	});  */

    	//alert(permValue.length==0 ?'你还没有选择任何内容！':permValue);//2,3,4
    	
    	var list=xtree.GetChecked();
        //alert(list); - object HTMLInputElement
        //console.log(list);
        
    	//菜单id
    	//var mStr="";
    	for(var i=0;i<list.length;i++){
    		//mStr+=list[i].value+",";
    		/* if(xtree.GetParent(list[i].value)!=null){
    			mStr+=xtree.GetParent(list[i].value).value+",";
    			if(xtree.GetParent(xtree.GetParent(list[i].value).value)!=null){
     				mStr+=xtree.GetParent(xtree.GetParent(list[i].value).value).value+",";
    			}
    		}else{
    			mStr+=list[i].value+",";
    		} */
    		
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
        
    	//去除字符串末尾的‘,’
    	/* mStr=mStr.substring(0,mStr.length-1);
        alert(mStr); */
    }

    var departmentParam = {};
    	departmentParam.deptCode;
    	departmentParam.deptname;
    	departmentParam.deptinfo;
    	departmentParam.isDis;

    	$("#save").click(function(){
    		var param = JSON.parse(JSON.stringify(departmentParam));
    		
    					param.deptCode=$("#deptCode").val();
    					param.deptname=$("#deptname").val();
    					param.deptinfo=$("#deptinfo").val();
    					
    					var isDis = $("input[name='isDisName']:checked").val();

    					param.isDis=isDis;
    					
    			//部门编码不可重复
    				
    			getPermValue();
    			
    			$.ajax({url:'<%=path %>/department/insert',
               		type:'post',
               		cache:false,
               		dataType:'json',
               		data: {GridParam:JSON.stringify(param),
               			permValue:permValue+""},
                   	success:function(data){
                   		if(data.code == "OK"){
                   			alert("数据保存成功");
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