<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
    $(function () {
        // 修改用户状态
        $(".operator").click(function () {
            // 获取当前按钮
            var currentElement = this;
            // 获取uid
            var uid = $(currentElement).val();
            // 地址url
            var urlPage = "admin-user-queryUserStatus";

            // ajax检验修改
            $.get(
                urlPage,
                {"uid":uid},
                function (result) {
                    // 启用状态，变成禁用
                    if ("start" == result) {
                        $(currentElement).attr("class", "btn btn-success operator");
                        $(currentElement).text("启用");
                    } else if ("disable" == result) {
                        // 禁用状态，变成启用
                        $(currentElement).attr("class", "btn btn-danger operator");
                        $(currentElement).text("禁用");
                    } else if ("fail" == result) {
                        return false;
                    }
                }
            )
        });
    });
</script>

<title>用户管理</title>

<div class="container">
    <br>
    <h1 class="label label-info">用户管理</h1>
    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="info">
                <th width="60px;">ID</th>
                <th>用户名</th>
                <th>邮箱</th>
                <th>电话</th>
                <th width="40px">性别</th>
                <th></th>
                <th>地址</th>
                <th>余额</th>
                <th width="100px;">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="u">
                <tr>
                    <td style="vertical-align: middle;">${u.id}</td>
                    <td style="vertical-align: middle;">${u.name}</td>
                    <td style="vertical-align: middle;">${u.mail}</td>
                    <td style="vertical-align: middle;">${u.mobile}</td>
                    <td style="vertical-align: middle;">
                        <c:if test="${0 == u.gender}">女</c:if>
                        <c:if test="${1 == u.gender}">男</c:if>
                    </td>
                    <td style="vertical-align: middle;">${u.qq}</td>
                    <td style="vertical-align: middle;">${u.address}</td>
                    <td style="vertical-align: middle;">${u.balance}</td>
                    <td>
                        <c:if test="${1 == u.status}">
                            <button class="btn btn-danger operator" style="height: 30px; font-size: 14px; outline: none;" value="${u.id}">禁用</button>
                        </c:if>
                        <c:if test="${0 == u.status}">
                            <button class="btn btn-success operator" style="height: 30px; font-size: 14px; outline: none;" value="${u.id}">启用</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>
</div>

<%@include file="../include/admin/adminFooter.jsp"%>