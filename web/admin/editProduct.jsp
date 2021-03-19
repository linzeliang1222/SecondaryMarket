<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>编辑产品</title>

<script>
    $(function() {
        var res = true;
        $("#seller").blur(function () {
            var seller = $("#seller").val();
            $.get(
                "admin-product-checkUser",
                {"seller":seller},
                function (result) {
                    if ("fail" == result) {
                        res = false;
                    } else {
                        res = true;
                    }
                }
            );
        });

        $("#editForm").submit(function() {
            if (!checkEmpty("name", "产品名称")) {
                return false;
            }
            if (!checkEmpty("description", "产品描述")) {
                return false;
            }
            if (!checkEmpty("originalPrice", "原始价格")) {
                return false;
            }
            if (!checkEmpty("promotePrice", "优惠价格")) {
                return false;
            }
            if (!checkEmpty("seller", "发布者")) {
                return false;
            }
            if (!res) {
                alert("该用户不存在！");
                return false;
            }

            return true;
        });
    });
</script>

<div class="container">
    <br>
    <ol class="breadcrumb">
        <li><a href="admin-category-list">所有分类</a></li>
        <li><a href="admin-product-list?cid=${product.category.id}">${product.category.name}</a></li>
        <li class="active">${product.name}</li>
        <li class="active">编辑产品</li>
    </ol>

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑产品</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin-product-update">
                <table class="editTable">
                    <tr>
                        <td>产品名称</td>
                        <td><input id="name" name="name" type="text" class="form-control" value="${product.name}"></td>
                    </tr>
                    <tr>
                        <td>产品描述</td>
                        <td><input id="description" name="description" type="text" class="form-control" value="${product.description}"></td>
                    </tr>
                    <tr>
                        <td>原价格</td>
                        <td><input id="originalPrice" name="originalPrice" type="text" class="form-control" value="${product.originalPrice}"></td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td><input id="promotePrice" name="promotePrice" type="text" class="form-control" value="${product.promotePrice}"></td>
                    </tr>
                    <tr>
                        <td>发布者</td>
                        <td><input id="seller" name="seller" type="text" class="form-control" value="${product.user.name}"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" value="${product.id}">
                            <input type="hidden" name="cid" value="${product.category.id}">
                            <input type="hidden" name="page.start" value="${page.start}">
                            <button type="submit" class="btn btn-success">修 改</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>