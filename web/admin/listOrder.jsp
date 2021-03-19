<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>

<script>
    $(function () {
        $("button.orderPageCheckOrderItems").click(function () {
            var oid = $(this).attr("oid");
            $("tr.orderPageOrderItemTR[oid=" + oid + "]").toggle();
        });

        // 发货
        $("button.delivery").click(function () {
            // 获取当前按钮
            var currentElement = this;
            // 获取oid
            var oid = $(currentElement).val();
            // 地址url
            var urlPage = "admin-order-delivery";

            $.get(
                urlPage,
                {"oid": oid},
                function (result) {
                    if ("success" == result) {
                        $(currentElement).hide();
                        $("td.status[oid=" + oid + "]").text("待收货");
                    }
                }
            );
        });
    });
</script>

<title>订单管理</title>

<div class="container">
    <br>
    <h1 class="label label-info">订单管理</h1>
    <br>
    <br>

    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover1  table-condensed">
            <thead>
            <tr class="info">
                <th width="60px">ID</th>
                <th width="60px">订单号</th>
                <th width="100px">买家名称</th>
                <th width="100px">创建时间</th>
                <th width="100px">支付时间</th>
                <th width="100px">发货时间</th>
                <th width="100px">确认收货时间</th>
                <th width="60px">状态</th>
                <th width="120px">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orders}" var="o">
                <tr>
                    <td style="vertical-align: middle;">${o.id}</td>
                    <td style="vertical-align: middle;">${o.orderCode}</td>
                    <td style="vertical-align: middle;">${o.user.name}</td>
                    <td style="vertical-align: middle;"><fmt:formatDate value="${o.createDate}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td style="vertical-align: middle;"><fmt:formatDate value="${o.payDate}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td style="vertical-align: middle;"><fmt:formatDate value="${o.deliveryDate}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td style="vertical-align: middle;"><fmt:formatDate value="${o.confirmDate}"
                                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td style="vertical-align: middle;" class="status" oid="${o.id}">${o.statusDescription}</td>
                    <td style="vertical-align: middle;">
                        <button oid="${o.id}" class="orderPageCheckOrderItems btn btn-warning btn-xs"
                                style="outline: none">查看详情
                        </button>
                        <c:if test="${o.status=='waitDelivery'}">
                            <button class="btn btn-success btn-xs delivery" value="${o.id}" style="outline: none">发货
                            </button>
                        </c:if>
                    </td>
                </tr>
                <tr class="orderPageOrderItemTR" oid=${o.id}>
                    <td colspan="10" align="center">
                        <div class="orderPageOrderItem">
                            <table width="800px" align="center" class="orderPageOrderItemTable">
                                <tr>
                                    <td>
                                        <img width="80px" height="80px"
                                             src="img/productSingle/${o.orderItem.product.firstProductImage.id}.jpg">
                                    </td>
                                    <td>
                                        <a href="foreproduct?pid=${o.orderItem.product.id}">
                                            <span>${o.orderItem.product.name}</span>
                                        </a>
                                    </td>
                                    <td>
                                        <span style="font-weight: bold; color: black">价格：</span><span
                                            style="font-weight: bold; color: red">￥<fmt:formatNumber type="number"
                                                                                                     value="${o.totalPrice}"
                                                                                                     minFractionDigits="2"/></span>
                                    </td>
                                    <td>
                                        <table>
                                            <tr>
                                                <td style="font-weight: bold; color: black; text-align: right;">收货人：
                                                </td>
                                                <td>${o.receiver}</td>
                                            </tr>
                                            <tr>
                                                <td style="font-weight: bold; color: black; text-align: right;">电话号码：
                                                </td>
                                                <td>${o.mobile}</td>
                                            </tr>
                                            <tr>
                                                <td style="font-weight: bold; color: black; text-align: right;">地址：</td>
                                                <td>${o.address}</td>
                                            </tr>
                                            <tr>
                                                <td style="font-weight: bold; color: black; text-align: right;">邮编：</td>
                                                <td>${o.post}</td>
                                            </tr>
                                            <tr>
                                                <td style="font-weight: bold; color: black; text-align: right;">备注信息：
                                                </td>
                                                <td>${o.userMessage}</td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </div>
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

<%@include file="../include/admin/adminFooter.jsp" %>