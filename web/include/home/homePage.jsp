<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>首页</title>

<!-- 内容S -->
<article>
    <div class="container" style="min-height: 600px">
        <div class="left btn-group-vertical">
            <ul class="list-group daohan">
                <li class="list-group-item bg-daohan">分类列表</li>
                <li class="list-group-item daohan-font <c:if test="${empty param.cid}">active disabled</c:if>"><a
                        href="forehome">所有商品</a></li>
                <c:forEach items="${categories}" var="c">
                    <li class="list-group-item daohan-font <c:if test="${param.cid==c.id}">active disabled</c:if>"><a
                            href="forehome?cid=${c.id}">${c.name}</a></li>
                </c:forEach>
            </ul>
            <div class="sort">
                <table>
                    <tr>
                        <td colspan="3" style="font-size: 18px; color: #000000; background-color: transparent;">
                            按条件筛选
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a class="sortLink" id="date" href="forecategorysort?<c:if test="${!empty hrefDate}">${hrefDate}</c:if><c:if test="${empty hrefDate}"><c:if test="${!empty param.cid}">cid=${param.cid}&</c:if>sort=dateDesc</c:if>">发布日期↓↑</a>
                        </td>
                        <td>
                            <a class="sortLink" id="price" href="forecategorysort?<c:if test="${!empty hrefPrice}">${hrefPrice}</c:if><c:if test="${empty hrefPrice}"><c:if test="${!empty param.cid}">cid=${param.cid}&</c:if>sort=priceAsc</c:if>">价格↓↑</a>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="text" class="form-control sortPrice beginPrice" style="font-size: 14px; padding: 0; width: 70px !important; text-align: center" value="${param.begin}">
                            &nbsp;~&nbsp;
                            <input type="text" class="form-control sortPrice endPrice" style="font-size: 14px; padding: 0; width: 70px !important; text-align: center" value="${param.end}">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <a href="forecategoryprice?<c:if test="${!empty param.cid}">cid=${param.cid}</c:if>" class="btn btn-info priceRange" style="height: 25px; color: white; font-size: 14px; padding-top: 1px">确定</a>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="right">
            <div aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                    <c:if test="${!empty param.cid}">
                        <c:forEach items="${categories}" var="c">
                            <c:if test="${param.cid==c.id}">
                                <li aria-current="page" class="breadcrumb-item active">${c.name}</li>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <li aria-current="page" class="breadcrumb-item active">所有商品</li>
                </ol>
            </div>

            <div class="products">
                <c:forEach items="${products}" var="p">
                    <div class="productUnit" price="${p.promotePrice}">
                        <a href="foreproduct?pid=${p.id}">
                            <img alt="" src="img/productSingleSmall/${p.firstProductImage.id}.jpg">
                        </a>
                        <span style="font-size: 20px; color: #bb0000; margin-left: 4px">￥<fmt:formatNumber
                                value="${p.promotePrice}" minFractionDigits="2"/></span><br>
                        <a class="simple-introduce" href="foreproduct?pid=${p.id}">
                            <c:if test="${fn:length(p.name) > 26 }">
                                ${fn:substring(p.name, 0, 25)}...
                            </c:if>
                            <c:if test="${fn:length(p.name) <= 26}">
                                ${p.name}
                            </c:if>
                        </a>
                    </div>
                </c:forEach>
            </div>

            <c:if test="${!empty products}">
                <div style="clear:both"></div>

                <div class="page">
                    <%@include file="../page.jsp" %>
                </div>
            </c:if>
            <c:if test="${empty products}">
                <span style="color: #bbbbbb; font-size: 20px; margin-left: 400px; display: block; padding-top: 150px">没有产品哦！</span>
            </c:if>
        </div>
    </div>
</article>
<div style="clear: both"></div>
<!-- 内容E -->