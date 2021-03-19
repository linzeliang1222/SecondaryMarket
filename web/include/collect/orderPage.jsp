<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>我的订单</title>

<script>
    var deleteOrder = false;
    var deleteOrderid = 0;

    $(function () {
        $("a[orderStatus]").click(function () {
            var orderStatus = $(this).attr("orderStatus");
            if ('all' == orderStatus) {
                $("table[orderStatus]").show();
            } else {
                $("table[orderStatus]").hide();
                $("table[orderStatus=" + orderStatus + "]").show();
            }
            $("div.orderType div").removeClass("selectedOrderType");
            $(this).parent("div").addClass("selectedOrderType");
        });

        $("a.deleteOrderLink").click(function () {
            deleteOrderid = $(this).attr("oid");
            switchDeleteModal();
        });

        $("button.confirmdelete").click(function () {
            $("button.hideModal").click();
            var page = "foredeleteOrder";
            $.post(
                page,
                {"oid": deleteOrderid},
                function (result) {
                    if ("success" == result) {
                        $("table.orderListItemTable[oid=" + deleteOrderid + "]").hide();
                    } else {
                        alert("无法删除未完成的订单！");
                        location.reload();
                    }
                }
            );
        });


    });
</script>

<!-- 内容S -->
<article>
    <div class="container">
        <div aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${contextPath}">主页</a></li>
                <li aria-current="page" class="breadcrumb-item active">订单详情</li>
            </ol>
        </div>

        <div class="boughtDiv">
            <div class="orderType">
                <div class="selectedOrderType"><a style="cursor: default" orderstatus="all">所有订单</a></div>
                <div><a style="cursor: default" orderstatus="waitPay">待付款</a></div>
                <div><a style="cursor: default" orderstatus="waitDelivery">待发货</a></div>
                <div><a style="cursor: default" orderstatus="waitConfirm">待收货</a></div>
                <div><a style="cursor: default" class="noRightborder" orderstatus="waitReview">待评价</a></div>
                <div class="orderTypeLastOne"></div>
            </div>
            <div style="clear:both"></div>
            <div class="orderListTitle">
                <table class="orderListTitleTable">
                    <tbody>
                    <tr>
                        <td>商品详情</td>
                        <td width="180px">订单号</td>
                        <td width="120px">实付款</td>
                        <td width="100px">交易操作</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <c:if test="${empty orders}"><span style="display: block; text-align: center; margin-top: 150px; color: #bbbbbb; font-size: 22px">没有订单记录哦！</span></c:if>
            <div class="boughtDiv">
                <div class="orderListItem" style="min-height: 500px">
                    <c:forEach items="${orders}" var="o">
                    <table class="orderListItemTable" oid="${o.id}" orderstatus="${o.status}">
                        <tbody>
                        <tr class="orderListItemFirstTR">
                            <td colspan="2">
                                <b><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${o.createDate}" /></b>
                            </td>
                            <td class="orderItemDeleteTD" colspan="4">
                                <a class="deleteOrderLink" style="cursor: pointer; font-weight: bold; font-size: 14px" oid="${o.id}">x</a>
                            </td>
                        </tr>
                        <tr class="orderItemProductInfoPartTR">
                            <td class="orderItemProductInfoPartTD" style="width: 80px; padding-right: 40px">
                                <img height="80" src="img/productSingleSmall/${o.orderItem.product.firstProductImage.id}.jpg" width="80">
                            </td>
                            <td class="orderItemProductInfoPartTD">
                                <div class="orderListItemProductLinkOutDiv" style="text-align: left;">
                                    <a class="productName" href="foreorderDetail?oid=${o.id}" style="color: #6d6d6d; font-size: 14px">${o.orderItem.product.name}</a>
                                </div>
                            </td>
                            <td class="orderListItemNumberTD orderItemOrderInfoPartTD" rowspan="1" valign="top"
                                width="100px">
                                <span style="font-weight: bold;">${o.orderCode}</span>
                            </td>
                            <td class="orderListItemProductRealPriceTD orderItemOrderInfoPartTD" rowspan="1"
                                valign="top" width="120px">
                                <div class="orderListItemProductRealPrice" style="color: #b30101">￥<fmt:formatNumber value="${o.orderItem.product.promotePrice}" /></div>
                                <div class="orderListItemPriceWithTransport">(含运费：￥0.00)</div>
                            </td>
                            <td valign="top" rowspan="1" class="orderListItemButtonTD orderItemOrderInfoPartTD" width="100px">
                                <c:if test="${o.status == 'waitConfirm'}">
                                    <a href="foreconfirmReceipt?oid=${o.id}">
                                        <button class="orderListItemConfirm">确认收货</button>
                                    </a>
                                </c:if>
                                <c:if test="${o.status=='waitPay'}">
                                    <a href="forepayment?oid=${o.id}&price=${o.orderItem.product.promotePrice}">
                                        <button class="orderListItemConfirm">付款</button>
                                    </a>
                                </c:if>
                                <c:if test="${o.status=='waitDelivery' }">
                                    <span>待发货</span>
                                </c:if>
                                <c:if test="${o.status=='waitReview'}">
                                    <a href="forereview?oid=${o.id}">
                                        <button class="orderListItemReview">评价</button>
                                    </a>
                                </c:if>
                                <c:if test="${o.status == 'finish'}">
                                    <span>交易完成</span>
                                </c:if>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</article>
<div style="clear: both"></div>
<!-- 内容E -->