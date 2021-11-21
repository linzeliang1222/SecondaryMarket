<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>搜索结果</title>

<!-- 内容S -->
<article>
    <div class="container">
        <div class="right" style="width: 100%; min-height: 550px">
            <div aria-label="breadcrumb" style="width: 53%; float: left;">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="/">主页</a></li>
                    <li aria-current="page" class="breadcrumb-item active">搜索结果（共 ${page.total} 条记录）：${param.keyword}</li>
                </ol>
            </div>

            <div class="btn-toolbar mb-3" role="toolbar" aria-label="Toolbar with button groups"
                 style="width: 47%; float: right; height: 53px; padding-left: 36px">
                <div class="btn-group mr-2" role="group" aria-label="First group">
                    <a style="padding-top: 12px; color: white" class="btn btn-info sortLink" id="date" href="foresearchsort?<c:if test="${!empty hrefDate}">${hrefDate}</c:if><c:if test="${empty hrefDate}"><c:if test="${!empty param.keyword}">keyword=${param.keyword}&</c:if>sort=dateDesc</c:if>">发布日期↓↑</a>
                    <a style="padding-top: 12px; color: white" class="btn btn-info sortLink" id="price" href="foresearchsort?<c:if test="${!empty hrefPrice}">${hrefPrice}</c:if><c:if test="${empty hrefPrice}"><c:if test="${!empty param.keyword}">keyword=${param.keyword}&</c:if>sort=priceAsc</c:if>">价格↓↑</a>
                </div>
                &nbsp;
                <div class="input-group">
                    <input type="text" class="form-control sortPrice beginPrice" style="height: 53px; width: 70px" value="${param.begin}">
                    <div class="input-group-prepend">
                        <div class="input-group-text" id="btnGroupAddon">~</div>
                    </div>
                    <input type="text" class="form-control sortPrice endPrice" style="height: 53px; width: 70px" value="${param.end}">
                </div>
                &nbsp;
                <a href="foresearchprice?keyword=${param.keyword}" class="btn btn-warning priceRange" style="color: #4e4e4e; padding-top: 12px">确定</a>
            </div>

            <div style="clear: both"></div>

            <c:if test="${empty products}"><span style="display: block; text-align: center; margin-top: 150px; margin-bottom: 150px; color: #bbbbbb; font-size: 22px">没有找到您想要的商品!</span></c:if>

            <div class="products">
                <c:forEach items="${products}" var="p">
                    <div class="productUnit">
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
                    <%@include file="./page.jsp" %>
                </div>
            </c:if>
        </div>
    </div>
</article>
<div style="clear: both"></div>
<!-- 内容E -->