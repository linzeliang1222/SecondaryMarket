<%@ page import="top.linzeliang.domain.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>用户详情 - ${sellerUser.name}</title>

<!-- 内容S -->
<article>
    <div class="container" style="min-height: 550px;">
        <div aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                <li aria-current="page" class="breadcrumb-item active">卖家信息：</li>
            </ol>
        </div>
        <div style="width: 850px; margin-left: 130px; margin-top: 30px">
            <div style="height: 100px; background-color: #d4ebf1; border-radius: 10px;">
                <table style="margin-top: 35px; display: inline-block; padding-left: 90px">
                    <tr>
                        <td style="width: 100px; font-weight: bold; font-size: 18px; text-align: right">卖家名称：</td>
                        <td style="width: 100px; text-align: center; border-bottom: 1px solid #1d1d1d; color: #ff6700">${sellerUser.name}</td>
                        <td style="width: 140px; font-weight: bold; font-size: 18px; text-align: right">商品数量：</td>
                        <td style="width: 60px; text-align: center; border-bottom: 1px solid #1d1d1d; color: #ff6700">${totalProduct}</td>
                        <td style="width: 140px; font-weight: bold; font-size: 18px; text-align: right">联系电话：</td>
                        <td style="width: 110px; text-align: left; border-bottom: 1px solid #1d1d1d; color: #ff6700">${sellerUser.mobile}</td>
                    </tr>
                </table>
            </div>
            <div style="margin-top: 20px;">
                <div>
                    <span style="color: black; font-weight: bold; font-size: 22px; padding-left: 10px">在售商品：</span>
                    <span style="color: black; font-weight: bold; font-size: 22px; padding-left: 370px">买家评价：</span>
                    <table style="display: block; float: left;">
                        <c:if test="${empty products}">
                            <tr style="width: 450px; display: block; margin: 15px 0px 15px 100px; height: 110px">
                                <td>
                                    <span style="display: block; text-align: center; margin-top: 150px; color: #bbbbbb; font-size: 18px">该用户没有发布商品哦！</span>
                                </td>
                            </tr>
                        </c:if>
                        <c:forEach items="${products}" var="product">
                            <tr style="border: 1px solid #c6c6c6; width: 450px; display: block; margin: 15px 0px 15px 30px; height: 110px">
                                <td>
                                    <img src="img/productSingleSmall/${product.firstProductImage.id}.jpg" alt=""
                                         style="width: 80px; height: 80px; margin-left: 20px; margin-top: 15px">
                                    <div style="display: inline-block; margin-left: 10px; vertical-align: top; margin-top: 20px">
                                        <a href="foreproduct?pid=${product.id}" class="productName"
                                           style="font-size: 14px; color: #4b4b4b">${product.name}
                                        </a>&nbsp;&nbsp;&nbsp;
                                        <span style="font-size: 12px; color: #ff6700">(${product.collectCount} 人收藏)</span>
                                        <br>
                                        <span style="font-size: 13px; color: #c11010;">￥<fmt:formatNumber
                                                value="${product.promotePrice}" minFractionDigits="2"/></span>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>

                    <div style="display: block; float: right;">
                        <c:if test="${empty reviews}">
                            <span style="display: block; margin-right: 120px; text-align: left; margin-top: 164px; color: #bbbbbb; font-size: 18px">该用户暂无评价！</span>
                        </c:if>
                        <c:forEach items="${reviews}" var="review">
                            <div class="card" style="width: 300px; margin: 15px; padding: 10px">
                                    ${review.content}
                                <div style="margin-top: 10px">
                                    <div style="text-align: left; display: inline-block; width: 70px; font-size: 12px; color: #686868">
                                        <fmt:formatDate value="${review.createDate}" pattern="yyyy-MM-dd"/>
                                    </div>
                                    <div style="text-align: right; display: inline-block; width: 200px">
                                        — <span
                                            style="font-weight: bold; font-size: 14px">${review.user.anonymousName}</span>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div style="clear: both;"></div>
                </div>
            </div>
        </div>
    </div>
</article>
<!-- 内容E -->