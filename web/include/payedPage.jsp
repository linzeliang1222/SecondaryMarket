<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>支付页面</title>

<script>
    $(function () {
        var price = new Number($("span.price").attr("price"));
        var balance = new Number($("span.balance").attr("balance"));
        if (price > balance) {
            alert("余额不足！");
            location.href = "foreorder";
        }
    });
</script>

<article>
    <div class="container" style="height: 570px;">
        <div class="payed" style="width: 350px; height: 250px; background-color: #d1f8ca; margin: 100px auto; border-radius: 5px">
            <div style="height: 50px; border-bottom: 1px solid #cbcbcb; text-align: center; padding-top: 8px; font-weight: bold; font-size: 20px">结 算</div>
            <div style="height: 130px; border-bottom: 1px solid #cbcbcb; text-align: center; padding-top: 30px; font-weight: bold;">
                <div style="margin-bottom: 20px">待支付价格：<span style="color: red" class="price" price="${order.orderItem.product.promotePrice}"><fmt:formatNumber value="${order.orderItem.product.promotePrice}" minFractionDigits="2" /></span></div>
                <div>余额：<span style="color: red" class="balance" balance="${user.balance}"><fmt:formatNumber value="${user.balance}" minFractionDigits="2" /></span></div>
            </div>
            <div style="height: 70px; text-align: center; padding-top: 12px; font-weight: bold; margin-top: 2px">
                <a href="forepayed?oid=${param.oid}" class="btn btn-success confirmPay">确认支付</a>
            </div>
        </div>
    </div>
</article>