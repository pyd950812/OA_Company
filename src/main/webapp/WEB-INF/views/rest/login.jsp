<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <base href="<%=basePath%>">
    <title>公司OA系统</title>
    <link rel="stylesheet" href="<%=path %>/assets/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path %>/assets/css/index.css">
    <script src="<%=path %>/assets/js/jquery/jquery-1.11.0.min.js"></script>
    <script src="<%=path %>/assets/js/bootstrap/bootstrap.min.js"></script>
    <script src="<%=path %>/assets/js/md5.js"></script>
    <!--[if lt IE 9]>
    <script src="<%=path %>/assets/js/html5shiv.min.js"></script>
    <script src="<%=path %>/assets/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<!--头部导航栏信息-->
<div class="navbar navbar-inverse" id="header">
    <div class="container-fluid">
        <div class="navbar-header header-left left">
            <span>公司OA平台</span>
        </div>
    </div>
</div>
<!--主体部分-->
<div class="LG-contain row">
    <!-- 屏蔽登录页的加载和跳转 -->
    <input type="hidden" value="LoginPage" id="hiddenLoginPage">
    <div id="LG-con-l" class="col-xs-4 col-xs-offset-2">
        <img src="<%=path %>/assets/image/login-1.png" class="LG-show">
    </div>
    <div id="LG-con-r" class="col-xs-5">
        <div class="LG-r">
            <%--  <form action = "<%=path %>/rest/loginAction" method = "post"> --%>
            <div class="LG-r-xm">
                <span>用户名:</span><br/>
                <input type="text" placeholder="请输入用户名" id="username" >
                <img src="<%=path %>/assets/image/login-u.png" class="login-input-img">
            </div>
            <div class="LG-r-mm">
                <span>密码:</span><br/>
                <input type="password" placeholder="请输入密码" id="password" >
                <img src="<%=path %>/assets/image/login-p.png" class="login-input-img">
            </div>
            <div class="LG-r-bot">
                <button id="LG-login">登录</button>
            </div>
            <!-- <div class="LG-r-bot2">
                <button id="LG-register">注册</button>
            </div> -->
            <!-- </form> -->
        </div>
    </div>
</div>
<div class="navbar LG-foot">
    <span>版权所有 © 2014-2018 华东交通大学14级pengyd's毕设</span>
</div>

<script>
    $(function () {
        var img = $('#LG-con-l img'),
            imglen = img.length;
        setInterval(function () {
            var ind = $('#LG-con-l img.LG-show').index();
            $(img[ind]).removeClass('LG-show').addClass('LG-hidden');
            ind = imglen - 1 == ind ? 0 : ind + 1;
            $(img[ind]).addClass("LG-show").removeClass('LG-hidden');
        }, 3000);

        //登录验证
        $('#LG-login').on('click', function (e) {
            e.preventDefault;
            var username = $('#username').val();
            var password = $('#password').val();
            if (username != "" && password != "") {
//             	username = hex_md5(username);     //加密部分
//             	password = hex_md5(password);
                $.ajax({
                    type: 'POST',
                    url: '<%=path %>/rest/loginByAjax',
                    dataType: 'json',
                    data: {'loginname': username, 'password': password},
                    success: function (res) {
                        if (res.code == 'OK') {
                            location.href = '<%=path %>/rest/main';
                        } else {
                            alert("登录失败，请检查用户名或密码！");
                        }
                    }
                });
            } else {
                alert("登录失败，请输入用户名或密码！");
            }
        });
    });
    document.onkeyup = function (e) {
        if (e.charCode || e.keyCode == 13) {
            $('#LG-login').click();
        }
    }
</script>


</body>

</html>