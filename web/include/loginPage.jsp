<!DOCTYPE html>
<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登 录</title>
    <link rel="stylesheet" type="text/css" href="css/fore/login.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap/4.4.1/bootstrap.min.css">
    <script src="js/jquery/3.3.1/jquery.min.js"></script>
    <script src="js/bootstrap/4.4.1/bootstrap.min.js"></script>

    <script>
        // 检验账号密码
        $(function () {
            <c:if test="${!empty errorMsg}">
                $("div.errorMsg").text("${errorMsg}");
                $("div.errorMsg").show();
            </c:if>

            $("#loginForm").submit(function () {
                if (0 == $("#name").val().length) {
                    $("div.errorMsg").text("请输入账号");
                    $("div.errorMsg").show();
                    return false;
                } else {
                    $("div.errorMsg").hide();
                }

                if (0 == $("#password").val().length) {
                    $("div.errorMsg").text("请输入密码");
                    $("div.errorMsg").show();
                    return false;
                } else {
                    $("div.errorMsg").hide();
                }
            });
        });
    </script>
</head>
<body>
    <div class="htmleaf-container">
        <div class="wrapper">
            <div class="container">
                <h1>登 录</h1>
                <form class="form"
                      action="forelogin<c:if test="${!empty param.url}">?url=<%=URLEncoder.encode(request.getParameter("url"))%></c:if>"
                      method="post" id="loginForm">
                    <input id="name" name="name" type="text" placeholder="账号">
                    <span></span>
                    <input id="password" name="password" type="password" placeholder="密码">
                    <span></span>
                    <button type="submit" id="login-button" style="outline: none;">进入</button>
                    <br/>
                    <a href="/" style="font-size: 14px;">←返回主页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="./register.jsp" style="font-size: 14px;">免费注册→</a>
                </form>
                <div class="alert alert-danger errorMsg" role="alert" style="max-width: 250px; margin: 0 auto; display: none"></div>
            </div>
        </div>
    </div>
</body>
</html>
