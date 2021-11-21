<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>我的收藏夹</title>

<article>
    <div class="container" style="min-height: 600px">
        <div aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                <li aria-current="page" class="breadcrumb-item active">我的收藏夹</li>
            </ol>
        </div>

        <div>
            <div>
                <table>
                    <tr style="height: 50px; font-size: 14px; font-weight: bold;">
                        <td style="width: 700px; text-align: center;">商品</td>
                        <td style="width: 170px; text-align: center;">原价</td>
                        <td style="width: 170px; text-align: center;">价格</td>
                        <td style="width: 100px; text-align: center;">操作</td>
                    </tr>
                </table>
                <c:if test="${empty orderItems}"><span style="display: block; text-align: center; margin-top: 150px; color: #bbbbbb; font-size: 22px">收藏夹是空的哦，赶快去<a href="${contextPath}">添加</a>吧！</span></c:if>
                <table style=" width: 100%; margin-top: 10px">
                    <c:forEach items="${orderItems}" var="oi">
                        <tr style="border: 1px solid #cdcdcd; height: 130px">
                            <td style="height: 80px; width: 700px">
                                <img src="img/productSingleSmall/${oi.product.firstProductImage.id}.jpg" alt=""
                                     style="width: 80px; height: 80px; margin-left: 50px">
                                <div style="display: inline-block; margin-left: 50px; width: 400px; vertical-align: top;">
                                    <a href="foreproduct?pid=${oi.product.id}"
                                       style="color: #505050; font-size: 14px;"
                                       class="productName">${oi.product.name}
                                    </a>
                                    <c:if test="${0 == oi.product.status}"><span style="color: red; font-size: 12px">(该商品已被购买)</span></c:if>
                                </div>
                            </td>
                            <td style="text-align: center; height: 80px; font-size: 14px; color: #878787; width: 170px; text-decoration: line-through">
                                ￥<fmt:formatNumber value="${oi.product.originalPrice}"
                                                   minFractionDigits="2"/>
                            </td>
                            <td style="text-align: center; height: 80px; color: #b31111; font-size: 18px; width: 170px">
                                ￥<fmt:formatNumber value="${oi.product.promotePrice}"
                                                   minFractionDigits="2"/>
                            </td>
                            <td style="text-align: center; height: 80px; color: #6a6a6a; font-size: 14px; width: 100px">
                                <a href="forecollectDelete?oiid=${oi.id}" style="color: #a0a0a0">X</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <c:if test="${!empty orderItems}">
                <div style="clear:both"></div>

                <div class="page">
                    <%@include file="../page.jsp" %>
                </div>
            </c:if>

        </div>
    </div>
</article>