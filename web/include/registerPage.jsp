<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注 册</title>
    <link rel="stylesheet" type="text/css" href="css/fore/login.css"/>
    <link rel="stylesheet" type="text/css" href="css/bootstrap/4.4.1/bootstrap.min.css">
    <script src="js/jquery/3.3.1/jquery.min.js"></script>
    <script src="js/bootstrap/4.4.1/bootstrap.min.js"></script>

    <script>
        // 检查注册的用户
        $(function () {
            $("#registerForm").submit(function () {
                var status = true;

                if (0 == $("#name").val().length) {
                    $("span.nameError").text("账号不能为空");
                    status = false;
                }

                if (0 == $("#password").val().length) {
                    $("span.passwordError").text("密码长度至少6位哦~");
                    status = false;
                }

                if (0 == $("#repeatpassword").val().length) {
                    $("span.repeatpasswordError").text("请输入重复密码");
                    status = false;
                }

                if ($("#password").val().length < 6) {
                    status = false;
                }

                if ($("#repeatpassword").val() != $("#password").val()) {
                    status = false;
                }

                if ("success" != $("span.nameError").attr("status")) {
                    return false;
                }

                return status;
            });

            $("#name").blur(function () {
                if (0 == $("#name").val().length) {
                    $("span.nameError").text("账号不能为空");
                } else {
                    $("span.nameError").text("");
                    // 判断账号是否存在
                    var urlPage = "forecheckUserExist";
                    var name = $("#name").val();

                    $.get(
                        urlPage,
                        {"name": name},
                        function (result) {
                            if ("success" == result) {
                                $("span.nameError").attr("status", "success");
                            } else {
                                $("span.nameError").text("账号已存在");
                                $("span.nameError").removeAttr("status");
                            }
                        }
                    );
                }
            });

            $("#password").blur(function () {
                if (6 > $("#password").val().length) {
                    $("span.passwordError").text("密码长度至少6位哦~");
                } else {
                    $("span.passwordError").text("");
                }

                if ($("#repeatpassword").val() != $("#password").val()) {
                    $("span.repeatpasswordError").text("两次输入的密码不一致");
                } else {
                    $(".repeatpasswordError").text("");
                }
            });

            $("#repeatpassword").blur(function () {
                if ($("#repeatpassword").val() != $("#password").val()) {
                    $("span.repeatpasswordError").text("两次输入的密码不一致");
                    return false;
                } else {
                    $(".repeatpasswordError").text("");
                    return true;
                }
            });
        });
    </script>
</head>
<body>
    <div class="htmleaf-container">
        <div class="wrapper">
            <div class="container">
                <h1>注 册</h1>
                <form class="form" method="post" action="foreregister" id="registerForm">
                    <input id="name" name="name" type="text" placeholder="账号">
                    <span class="nameError"></span>
                    <input id="password" name="password" type="password" placeholder="密码">
                    <span class="passwordError"></span>
                    <input id="repeatpassword" name="repeatpassword" type="password" placeholder="确认密码">
                    <span class="repeatpasswordError"></span>
                    <button type="submit" id="login-button" style="outline: none">提 交</button>
                    <br/>
                    <a href="/" style="font-size: 14px;">←返回主页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="./login.jsp" style="font-size: 14px;">去登陆→</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>