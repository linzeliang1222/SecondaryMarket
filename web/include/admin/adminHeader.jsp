<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <script src="js/jquery/3.3.1/jquery.min.js"></script>
    <link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
    <script src="js/bootstrap/4.4.1/bootstrap.min.js"></script>
    <link href="css/back/style.css" rel="stylesheet">
    <script>
        function checkEmpty(id, name) {
            var value = $("#" + id).val();
            if (value.length == 0) {
                alert(name + "不能为空");
                $("#" + id)[0].focus();
                return false;
            }
            return true;
        }

        function checkNumber(id, name) {
            var value = $("#" + id).val();
            if (value.length == 0) {
                alert(name + "不能为空");
                $("#" + id)[0].focus();
                return false;
            }
            if (isNaN(value)) {
                alert(name + "必须是数字");
                $("#" + id)[0].focus();
                return false;
            }
            return true;
        }

        function checkInt(id, name) {
            var value = $("#" + id).val();
            if (value.length == 0) {
                alert(name + "不能为空");
                $("#" + id)[0].focus();
                return false;
            }
            if (parseInt(value) != value) {
                alert(name + "必须是整数");
                $("#" + id)[0].focus();
                return false;
            }
            return true;
        }

        $(function () {
                // 对于删除超链，都需要进行确认操作
                $("a").click(function () {
                    var deleteLink = $(this).attr("deleteLink");
                    console.log(deleteLink);
                    if ("true" == deleteLink) {
                        var confirmDelete = confirm("确认要删除");
                        if (confirmDelete) {
                            return true;
                        }
                        return false;
                    }
                });
            });
    </script>
</head>
<body>
