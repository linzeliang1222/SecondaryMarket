<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link href="css/fore/reset.css" rel="stylesheet">
    <link href="css/fore/style.css" rel="stylesheet">
    <script src="js/jquery/3.3.1/jquery.min.js"></script>
    <link href="css/bootstrap/4.4.1/bootstrap.min.css" rel="stylesheet">
    <script src="js/bootstrap/4.4.1/bootstrap.min.js"></script>
    <link rel="icon" type="image/x-icon" href="img/other/logo.jpg"/>
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

            // 价格筛选只能输入整数
            $("input.sortPrice").blur(function() {
                var num = $(this).val();

                num = parseInt(num);
                if (isNaN(num))
                    num = "";
                if (num <= 0)
                    num = "";
                $(this).val(num);
            });

            // 点击排序的时候附带上价格范围
            $("a.sortLink").click(function () {
                var begin = $("input.beginPrice").val();
                var end = $("input.endPrice").val();

                var date_href = $("#date").attr("href");
                var price_href = $("#price").attr("href");

                if (0 != begin.length) {
                    date_href += "&begin=" + begin;
                    price_href += "&begin=" + begin;
                }
                if (0 != end.length) {
                    date_href += "&end=" + end;
                    price_href += "&end=" + end;
                }

                if ("date" == $(this).attr("id")) {
                    location.href = date_href;
                } else if ("price" == $(this).attr("id")) {
                    location.href = price_href;
                }

                return false;
            });

            // 仅按照价格筛选
            $("a.priceRange").click(function () {
                var begin = $("input.beginPrice").val();
                var end = $("input.endPrice").val();

                var price_range_href = $(this).attr("href");

                if (0 != begin.length) {
                    price_range_href += "&begin=" + begin;
                }
                if (0 != end.length) {
                    price_range_href += "&end=" + end;
                }

                location.href = price_range_href;

                return false;
            });
        });
    </script>
</head>
<body>