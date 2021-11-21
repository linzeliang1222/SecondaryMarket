<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>订单详情</title>

<article>
    <div class="container" style="min-height: 600px">
        <div aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                <li aria-current="page" class="breadcrumb-item active">订单详情：${order.orderItem.product.name}</li>
            </ol>
        </div>

        <div style="margin-left: 155px; padding-top: 30px">
            <table style="border: 1px solid #e3e3e3">
                <thead style="height: 20px; background-color: #e8f2ff; font-size: 12px; font-weight: normal; border: 1px solid #e3e3e3">
                    <tr>
                        <th style="width: 500px; text-align: center">产品</th>
                        <th style="width: 100px; text-align: center">价格</th>
                        <th style="width: 100px; text-align: center">实付款</th>
                        <th style="width: 100px; text-align: center">运费</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td style="height: 80px;">
                            <img src="img/productSingleSmall/${order.orderItem.product.firstProductImage.id}.jpg" alt="" style="width: 50px; height: 50px; margin-left: 20px">
                            <div style="display: inline-block; margin-left: 10px; width: 400px; vertical-align:middle;">
                                <a href="foreproduct?pid=${order.orderItem.product.id}" style="color: #bbbbbb; font-size: 14px;" class="productName">${order.orderItem.product.name}</a>
                            </div>
                        </td>
                        <td style="text-align: center; height: 80px; font-size: 14px; color: #878787">
                            ￥<fmt:formatNumber value="${order.orderItem.product.promotePrice}" minFractionDigits="2" />
                        </td>
                        <td style="text-align: center; height: 80px; color: #b31111; font-size: 20px; font-weight: bold">
                            ￥<fmt:formatNumber value="${order.orderItem.product.promotePrice}" minFractionDigits="2" />
                        </td>
                        <td style="text-align: center; height: 80px; color: #6a6a6a; font-size: 14px">
                            0.00
                        </td>
                    </tr>
                </tbody>
            </table>

            <div style="margin-top: 30px; border-top: 1px solid #cdcdcd; width: 760px; padding-top: 15px; margin-left: 20px">
                <table style="margin-left: 10px">
                    <tr>
                        <td style="width: 150px; font-weight: bold; font-size: 16px; text-align: center; height: 50px">订单号：</td>
                        <td style="width: 250px; text-align: left; font-size: 16px; padding-left: 10px">${order.orderCode}</td>
                    </tr>
                    <tr>
                        <td style="width: 150px; font-weight: bold; font-size: 16px; text-align: center; height: 50px">卖家名称：</td>
                        <td style="width: 250px; text-align: left; font-size: 16px; padding-left: 10px">${order.orderItem.product.user.name}</td>
                    </tr>
                    <tr>
                        <td style="width: 150px; font-weight: bold; font-size: 16px; text-align: center; height: 50px">收货信息：</td>
                        <td style="width: 250px; text-align: left; font-size: 16px; padding-left: 10px">${order.receiver}, ${order.mobile}, ${order.address}</td>
                    </tr>
                    <tr>
                        <td style="width: 150px; font-weight: bold; font-size: 16px; text-align: center; height: 50px">创建日期：</td>
                        <td style="width: 250px; text-align: left; font-size: 16px; padding-left: 10px">
                            <fmt:formatDate value="${order.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 150px; font-weight: bold; font-size: 16px; text-align: center; height: 50px">付款日期：</td>
                        <td style="width: 250px; text-align: left; font-size: 16px; padding-left: 10px">
                            <fmt:formatDate value="${order.payDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 150px; font-weight: bold; font-size: 16px; text-align: center; height: 50px">发货日期：</td>
                        <td style="width: 250px; text-align: left; font-size: 16px; padding-left: 10px">
                            <fmt:formatDate value="${order.deliveryDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 150px; font-weight: bold; font-size: 16px; text-align: center; height: 50px">收货日期：</td>
                        <td style="width: 250px; text-align: left; font-size: 16px; padding-left: 10px">
                            <fmt:formatDate value="${order.confirmDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 150px; font-weight: bold; font-size: 16px; text-align: center; height: 50px">备注信息：</td>
                        <td style="width: 250px; text-align: left; font-size: 16px; padding-left: 10px">${order.userMessage}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</article>