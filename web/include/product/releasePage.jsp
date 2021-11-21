<%@ page import="top.linzeliang.domain.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>我发布的商品</title>

<!-- 内容S -->
<article>
    <div class="container" style="min-height: 550px;">
        <div aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                <li aria-current="page" class="breadcrumb-item active">商品管理</li>
            </ol>
        </div>

        <span style="color: black; font-weight: bold; font-size: 22px; padding-left: 10px; margin-left: 130px">我发布的：</span>
        <c:if test="${empty products}"><span
                style="display: block; text-align: center; margin-top: 150px; color: #bbbbbb; font-size: 22px">未发布商品哦，赶快去<a
                href="foreaddProduct">发布</a>吧！</span></c:if>
        <div style="width: 800px; margin-left: 155px; margin-top: 30px">
            <div>
                <table style="display: block;">
                    <c:forEach items="${products}" var="product">
                        <tr style="border: 1px solid #c6c6c6; width: 800px; height: 90px">
                            <td style="width: 600px">
                                <img src="img/productSingleSmall/${product.firstProductImage.id}.jpg" alt=""
                                     style="width: 70px; height: 70px; margin-left: 20px; margin-right: 20px">
                                <div style="display: inline-block; margin-left: 10px; vertical-align: top; margin-top: 20px">
                                    <a href="foreproduct?pid=${product.id}" class="productName"
                                       style="font-size: 14px; color: #4b4b4b">
                                            ${product.name} <c:if test="${0 == product.status}"><span
                                            style="color: red; font-size: 12px">(已售)</span></c:if>
                                    </a>
                                </div>
                            </td>
                            <c:if test="${1 == product.status}">
                                <td style="width: 100px; text-align: center;">
                                    <a class="productName" href="foreeditProduct?pid=${product.id}"
                                       style="color: #575757; font-size: 14px">编辑商品</a>
                                </td>
                                <td style="width: 100px; text-align: center;">
                                    <a class="productName" href="foreupdateProductImage?pid=${product.id}"
                                       style="color: #575757; font-size: 14px">图片管理</a>
                                </td>
                                <td style="width: 100px; text-align: center">
                                    <a href="foredeleteProduct?pid=${product.id}" style="color: #ea0000">x</a>
                                </td>
                            </c:if>
                            <c:if test="${'waitDelivery' == product.progress}">
                                <td style="width: 100px; text-align: center;"></td>
                                <td style="width: 100px; text-align: center;"></td>
                                <td style="width: 100px; text-align: center">
                                    <a href="foredeliveryProduct?pid=${product.id}" style="font-size: 14px;" class="btn btn-warning">确认发货</a>
                                </td>
                            </c:if>
                            <c:if test="${0 == product.status && 'waitDelivery' != product.progress}">
                                <td style="width: 100px; text-align: center;"></td>
                                <td style="width: 100px; text-align: center;"></td>
                                <td style="width: 100px; text-align: center">
                                    <span class="card" style="width: 85px; margin-left: 7px; height: 40px; padding-top: 8px; font-size: 14px; font-weight: bold">
                                        <c:if test="${'waitPay' == product.progress}">待支付</c:if>
                                        <c:if test="${'waitConfirm' == product.progress}">待收货</c:if>
                                        <c:if test="${'waitReview' == product.progress}">待评价</c:if>
                                        <c:if test="${'finish' == product.progress}">完成</c:if>
                                    </span>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <c:if test="${!empty products}">
                <div style="clear:both"></div>

                <div class="page">
                    <%@include file="../page.jsp" %>
                </div>
            </c:if>
        </div>
    </div>
</article>
<!-- 内容E -->