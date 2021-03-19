<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<c:if test="${fn:length(product.name) > 10}">
    <title>产品详情 - ${fn:substring(product.name, 0, 10)}...</title>
</c:if>
<c:if test="${fn:length(product.name) <= 10}">
    <title>产品详情 - ${product.name}</title>
</c:if>

<script>
    $(function () {
        // 是否已售
        if ("true" ==  $(".buynow").attr("bought")) {
            $(".buynow").html("已被购买");
            $(".buynow").attr("disabled","disabled");
            $(".buynow").css("background-color","lightgray");
            $(".buynow").css("border-color","lightgray");
            $(".buynow").css("color","black");
            $(".buynow").css("cursor","no-drop");

            $(".addcollect").html("添加收藏");
            $(".addcollect").attr("disabled","disabled");
            $(".addcollect").css("background-color","lightgray");
            $(".addcollect").css("border-color","lightgray");
            $(".addcollect").css("color","black");
            $(".addcollect").css("cursor","no-drop");
        }

        // 是否已收藏
        if ("true" ==  $(".addcollect").attr("collect")) {
            $(".addcollect").html("已收藏了");
            $(".addcollect").attr("disabled","disabled");
            $(".addcollect").css("background-color","lightgray");
            $(".addcollect").css("border-color","lightgray");
            $(".addcollect").css("color","black");
            $(".addcollect").css("cursor","no-drop");
        }

        // 点购买
        $("a.buynow").click(function () {
            if ("true" ==  $(".buynow").attr("bought")) {
                return false;
            }
            var urlPage = "forecheckLogin";
            $.get(
                urlPage,
                function (result) {
                    if ("success" == result) {
                        location.href = $("a.buynow").attr("href");
                    } else {
                        switchLoginModal();
                    }
                }
            );

            return false;
        });

        // 添加购物车
        $("a.addcollect").click(function () {
            if ("true" ==  $(".buynow").attr("bought")) {
                return false;
            }
            var urlPage = "forecheckLogin";
            $.get(
                urlPage,
                function (result) {
                    if ("success" == result) {
                        var pid = ${product.id};
                        var addCartPage = "foreaddCollect";
                        $.get(
                            addCartPage,
                            {"pid":pid},
                            function(result){
                                if("success" == result){
                                    location.href += "&collect=true";
                                } else if ("exist" == result && "disabled" != $(".addcollect").attr("disabled")) {
                                    $(".addcollect").html("已收藏过");
                                    $(".addcollect").attr("disabled","disabled");
                                    $(".addcollect").css("background-color","lightgray");
                                    $(".addcollect").css("border-color","lightgray");
                                    $(".addcollect").css("color","black");
                                    $(".addcollect").css("cursor","no-drop");
                                } else if ("fail" == result) {
                                    $(".addcollect").html("收藏失败");
                                    $(".addcollect").attr("disabled","disabled");
                                    $(".addcollect").css("background-color","lightgray");
                                    $(".addcollect").css("border-color","lightgray");
                                    $(".addcollect").css("color","black");
                                    $(".addcollect").css("cursor","no-drop");
                                    alert("不能收藏自己发布的商品！")
                                }
                            }
                        );
                    } else{
                        // 未登录则显示登录模态界面
                        switchLoginModal();
                    }
                }
            );

            return false;
        });
    });
</script>

<!-- 内容S -->
<article>
    <div class="container">
        <div aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                <li aria-current="page" class="breadcrumb-item active">${product.name}</li>
                <li aria-current="page" class="breadcrumb-item active">商品详情</li>
            </ol>
        </div>
        <div class="imgAndInfo">
            <div class="imgInImgAndInfo" style="border: 1px solid #b1b1b1; padding: 10px">
                <img src="img/productSingle/${product.firstProductImage.id}.jpg">
            </div>
            <div class="infoInImgAndInfo">
                <div class="titleDiv" style="height: 70px;"><span class="productTitle">${product.name}</span></div>
                <div class="originalDiv">原价：<span class="productOriginalPrice">￥<fmt:formatNumber
                        value="${product.originalPrice}" minFractionDigits="2"/></span></div>
                <div class="promoteDiv">价格：<span class="productPromotePrice">￥<fmt:formatNumber
                        value="${product.promotePrice}" minFractionDigits="2"/></span></div>
                <div class="authorDiv">发布者：<a href="foreuserMessage?uid=${product.user.id}"><span
                        class="productAuthor">${product.user.name}</span></a></div>
                <div class="timeDiv">发布时间：<span class="productCreateTime"><fmt:formatDate value="${product.createDate}"
                                                                                          pattern="yyyy-MM-dd HH:mm"/></span>
                </div>
                <div class="buyButton">
                    <a class="btn btn-danger buynow" href="forebuy?pid=${product.id}" bought="<c:if test="${product.status == 0}">true</c:if>">立即购买</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a class="btn btn-warning addcollect" href="javascript:void(0)" collect="${param.collect}">添加收藏</a>
                </div>
            </div>
        </div>
        <div class="new-title">
            <div class="alert-success alert">最新发布</div>
            <c:forEach items="${newproducts}" var="np">
                <div class="new-product">
                    <a href="foreproduct?pid=${np.id}"><img alt="" src="img/productSingleSmall/${np.firstProductImage.id}.jpg"></a>
                    <div class="new-product-right">
                        <div class="new-product-title">${np.name}</div>
                        <div class="new-product-price">￥<fmt:formatNumber value="${np.originalPrice}" minFractionDigits="2"/></div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div style="clear: both"></div>

        <div class="productDetail">
            <div class="productParamterPart">
                <div class="detailTitle alert-primary alert">详细介绍</div>
                <div style="clear:both"></div>
            </div>
            <div class="descriptionDiv"><span style="font-weight: bold; font-size: 20px">物品说明：</span><span class="productDescription">${product.description}</span></div>
            <hr/>
            <c:forEach items="${product.productDetailImages}" var="pdi">
                <div class="productDetailImagesPart">
                    <img src="img/productDetail/${pdi.id}.jpg">
                </div>
            </c:forEach>
        </div>
    </div>
</article>
<div style="clear: both"></div>
<!-- 内容E -->