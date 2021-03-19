<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<title>产品管理</title>

<script>
    $(function () {
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

        $("#addForm").submit(function () {
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
        <li><a href="admin-product-list?cid=${category.id}">${category.name}</a></li>
        <li class="active">产品管理</li>
    </ol>
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
                <tr class="info">
                    <th width="40px">ID</th>
                    <th width="70px">图片</th>
                    <th>产品名称</th>
                    <th width="150px">产品描述</th>
                    <th width="70px">原始价格</th>
                    <th width="70px">优惠价格</th>
                    <th width="70px">图片管理</th>
                    <th width="80px">发布者</th>
                    <th width="150px">发布时间</th>
                    <th width="40px">编辑</th>
                    <th width="40px">删除</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${products}" var="p">
                <tr>
                    <td style="vertical-align: middle">${p.id}</td>
                    <td>
                        <c:if test="${!empty p.firstProductImage}">
                            <img width="40px" src="img/productSingle/${p.firstProductImage.id}.jpg" alt="加载失败...">
                        </c:if>
                    </td>
                    <td style="vertical-align: middle">
                        <c:if test="${fn:length(p.name) > 40 }">
                            ${fn:substring(p.name, 0, 40)}
                        </c:if>
                        <c:if test="${fn:length(p.name) <= 40 }">
                            ${p.name}
                        </c:if>
                    </td>
                    <td style="vertical-align: middle">${p.description}</td>
                    <td style="vertical-align: middle">
                        <fmt:formatNumber type="number" value="${p.originalPrice}" minFractionDigits="2"/>
                    </td>
                    <td style="vertical-align: middle">
                        <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/>
                    </td>
                    <td style="vertical-align: middle">
                        <a href="admin-productImage-list?pid=${p.id}"><span class="glyphicon glyphicon-picture"></span></a>
                    </td>
                    <td style="vertical-align: middle">${p.user.name}</td>
                    <td style="vertical-align: middle">
                        <fmt:formatDate value="${p.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td style="vertical-align: middle">
                        <a href="admin-product-edit?id=${p.id}&page.start=${page.start}"><span class="glyphicon glyphicon-edit"></span></a>
                    </td>
                    <td style="vertical-align: middle">
                        <a deleteLink="true" href="admin-product-delete?id=${p.id}&page.start=${page.start}"><span
                                class="glyphicon glyphicon-trash"></span></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>

    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增产品</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="admin-product-add">
                <table class="addTable">
                    <tr>
                        <td>产品名称</td>
                        <td><input id="name" name="name" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>产品描述</td>
                        <td><input id="description" name="description" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>原价格</td>
                        <td><input id="originalPrice" name="originalPrice" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>优惠价格</td>
                        <td><input id="promotePrice" name="promotePrice" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>发布者</td>
                        <td><input id="seller" name="seller" type="text" class="form-control"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="cid" value="${category.id}">
                            <button type="submit" class="btn btn-success">添 加</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>