<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<title>分类管理</title>

<script>
    $(function () {
        $("#addForm").submit(function () {
            if (!checkEmpty("name", "分类名称")) {
                return false;
            }
        });
    });
</script>

<div class="container">
    <br>
    <h1 class="label label-info">分类管理</h1>
    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover table-condensed">
            <thead>
            <tr class="info">
                <th width="120px">ID</th>
                <th>分类名称</th>
                <th width="100px">产品管理</th>
                <th width="100px">修改</th>
                <th width="100px">删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${categories}" var="c">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.name}</td>
                    <td><a href="admin-product-list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>
                    <td><a href="admin-category-edit?id=${c.id}&page.start=${page.start}"><span class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a href="admin-category-delete?id=${c.id}&page.start=${page.start}" deleteLink="true" ><span class="glyphicon glyphicon-trash"></span></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>

    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增分类</div>
        <div class="panel-body">
            <form action="admin-category-add" id="addForm" method="post">
                <table class="addTable">
                    <tr>
                        <td>分类名称</td>
                        <td><input type="text" id="name" name="name" class="form-control categoryName"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <button type="submit" class="btn btn-success">添 加</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp" %>
