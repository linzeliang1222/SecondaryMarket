<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>支付成功</title>

<article>
    <div class="container" style="height: 570px;">
        <div style="width: 350px; height: 250px; margin: 100px auto; border-radius: 5px">
            <div style="height: 50px; background-color: #ecffdc; padding-left: 10px; padding-top: 10px">
                <img src="img/other/paySuccess.png" alt="" style="display: inline-block;">
                <div style="display: inline-block; margin-left: 2px; font-weight: bold; font-size: 16px">您已成功付款</div>
            </div>
            <div style="height: 150px; background-color: #f1f1ef; padding-left: 50px; padding-top: 40px; font-size: 14px">
                <a href="${contextPath}" style="margin-bottom: 20px; display: inline-block">返回主页</a>
                <br>
                <a href="foreorderDetail?oid=${order.id}">查看订单详情</a>
            </div>
        </div>
    </div>
</article>