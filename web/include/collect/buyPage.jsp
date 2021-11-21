<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>创建订单</title>

<script>
    $(function () {
        // 判断余额是否充足
        var pid = $("input.pid").attr("pid");
        var urlPage = "forecheckBalance";
        $.get(
            urlPage,
            {"pid":pid},
            function (result) {
                if ("fail" == result) {
                    alert("余额不足！");
                    location.href = "/";
                }
            }
        );

        // 判断是否购买了
        if ("true" == $("button.commitorder").attr("bought")) {
            $(".commitorder").html("已售");
            $(".commitorder").attr("disabled","disabled");
            $(".commitorder").css("background-color","lightgray");
            $(".commitorder").css("border-color","lightgray");
            $(".commitorder").css("color","black");
            $(".commitorder").css("cursor","no-drop");
        }

        $(".createOrderForm").submit(function () {
            if ($("#receiver").val().length == 0) {
                $("#receiver").attr("class", "form-control is-invalid")
                return false;
            } else {
                $("#receiver").attr("class", "form-control is-valid")
            }

            if ($("#mobile").val().length == 0 || $("#mobile").val().length != 11) {
                $("#mobile").attr("class", "form-control is-invalid")
                return false;
            } else {
                $("#mobile").attr("class", "form-control is-valid");
            }

            if ($("#address").val().length == 0) {
                $("#address").attr("class", "form-control is-invalid");
                return false;
            } else {
                $("#address").attr("class", "form-control is-valid");
            }

            return true;
        });

        $("#myAddress").blur(function () {
            var address = $(this).val();
            if (address.length == 0) {
                $(".addressError").show();
            } else {
                $(".addressError").hide();
            }
        });

        $(".receiver").blur(function () {
            var receiver = $(this).val();
            if (receiver.length == 0) {
                $(".receiverError").show();
            } else {
                $(".receiverError").hide();
            }
        });

        $(".mobile").blur(function () {
            var mobile = $(this).val();
            if (mobile.length == 0) {
                $(".mobileError").html("请输入手机号码");
                $(".mobileError").show();
            } else if (mobile.length != 11) {
                $(".mobileError").html("手机号码有误");
                $(".mobileError").show();
            } else {
                $(".mobileError").hide();
            }
        });
    });
</script>

<!-- 内容S -->
<article>
    <div class="container">
        <div class="right" style="width: 100%">
            <div aria-label="breadcrumb">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                    <li aria-current="page" class="breadcrumb-item active">${product.name}</li>
                    <li aria-current="page" class="breadcrumb-item active">购买</li>
                </ol>
            </div>

            <div class="buyInformation">
                <div class="productInfo">
                    <span style="font-size: 20px; font-weight: bold; margin-left: 20px; padding-top: 20px; display: inline-block">确认订单信息</span>
                    <div class="productLink" style="padding-left: 35px;">
                        <img src="img/productSingleSmall/${product.firstProductImage.id}.jpg" alt="">
                        <div style="padding-left: 10px"><a href="foreproduct?pid=${product.id}">${product.name}</a></div>
                    </div>
                    <table style="margin-left: 10px">
                        <tr>
                            <td>价格</td>
                            <td>￥<fmt:formatNumber value="${product.promotePrice}" minFractionDigits="2"/></td>
                        </tr>
                        <tr>
                            <td>配送方式</td>
                            <td style="padding-top: 20px !important; padding-left: 40px">
                                <div class="form-group" style="width: 100px;">
                                    <select class="form-control" style="font-size: 10px">
                                        <option selected>邮费 0 元</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>实付款</td>
                            <td style="color: #ec3131; font-weight: bold; font-size: 20px;">￥<fmt:formatNumber value="${product.promotePrice}" minFractionDigits="2"/></td>
                        </tr>
                    </table>
                </div>
                <div class="addressInfo">
                    <form action="forecreateOrder" method="post" class="createOrderForm">
                        <div colspan="2" style="font-size: 20px; font-weight: bold; margin-left: 20px; margin-top: 20px">输入收货信息</div>
                        <table>
                            <tr>
                                <td>收货人姓名</td>
                                <td><input id="receiver" name="receiver" class="form-control" type="text" placeholder="请输入收货人姓名" style="font-size: 16px !important; "></td>
                                <td rowspan="5" style="; display: inline-block; width: 150px; margin-left: 20px">
                                    <input type="hidden" class="pid" pid="${param.pid}" name="pid" value="${param.pid}">
                                    <button class="btn btn-info commitorder" bought="<c:if test="${product.status == 0}">true</c:if>">创建订单</button>
                                </td>
                            </tr>
                            <tr>
                                <td>手机号</td>
                                <td><input id="mobile" name="mobile" class="form-control" type="text" placeholder="请输入11位手机号码" style="font-size: 16px !important;"></td>
                            </tr>
                            <tr>
                                <td>邮政编码</td>
                                <td><input id="post" name="post" class="form-control" type="text" placeholder="默认(000000)" style="font-size: 16px !important; "></td>
                            </tr>
                            <tr>
                                <td>详细地址</td>
                                <td><textarea placeholder="建议您如实填写详细收货地址，例如接到名称，门牌号码，楼层和房间号等信息" id="address" name="address" class="form-control" style="font-size: 14px !important; width: 300px !important; resize: none; height: 80px; margin-bottom: 28px; margin-top: 14px"></textarea></td>
                            </tr>
                            <tr>
                                <td>留言</td>
                                <td><textarea id="userMessage" name="userMessage" class="form-control" placeholder="给卖家留言" style="font-size: 14px !important; width: 300px !important; resize: none; height: 80px; margin-bottom: 20px"></textarea></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</article>
<div style="clear: both"></div>
<!-- 内容E -->