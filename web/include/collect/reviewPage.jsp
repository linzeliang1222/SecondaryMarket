<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<title>评价</title>

<article>
    <div class="container" style="min-height: 600px">
        <div class="reviewDiv" style="margin-top: 50px">
            <div class="reviewProductInfoDiv">
                <div class="reviewProductInfoImg"
                     style="float: left; padding: 20px; border: 1px solid #c4c4c4; display: inline-block; margin-right: 20px; margin-left: 50px">
                    <img width="300px" height="300px"
                         src="img/productSingle/${order.orderItem.product.firstProductImage.id}.jpg">
                </div>
                <div class="reviewProductInfoRightDiv" style="float: left; background-color: #efefef ; height: 342px; width: 660px; border-radius: 6px">
                    <div class="reviewProductInfoRightText"
                         style="font-weight: bold; padding-left: 20px; font-size: 22px; margin-bottom: 6px">
                        ${order.orderItem.product.name}
                    </div>
                    <table class="reviewProductInfoTable" style="margin-left: 35px; margin-top: 10px">
                        <tr style="font-size: 14px; color: #8b8b8b">
                            <td width="75px">实付款:</td>
                            <td><span class="reviewProductInfoTablePrice"
                                      style="color: #ba1818; font-size: 20px">￥<fmt:formatNumber type="number"
                                                                                                 value="${order.orderItem.product.originalPrice}"
                                                                                                 minFractionDigits="2"/></span>
                                &nbsp;&nbsp;元
                            </td>
                        </tr>
                        <tr style="font-size: 14px; color: #8b8b8b; padding-bottom: 200px">
                            <td>配送:</td>
                            <td>免运费</td>
                        </tr>
                    </table>
                    <div class="reviewProductInfoRightBelowDiv">
                        <span class="reviewProductInfoRightBelowText" style="font-size: 16px; font-weight: bold; margin-left: 34px">您所评价的商品的信息于 <span style="color: #ff6700"><fmt:formatDate value="${order.orderItem.product.createDate}" pattern="yyyy年MM月dd日"/></span> 下单购买了此商品</span>
                    </div>
                    <div class="makeReviewDiv" style="text-align: center">
                        <form method="post" action="foredoreview">
                            <table class="makeReviewTable" style="margin-left: 30px; margin-top: 20px">
                                <tr style="height: 140px; background-color: white">
                                    <td class="makeReviewTableFirstTD" style="border-right: 1px solid #a7a7a7; width: 100px; text-align: center; color: gray">填写评价</td>
                                    <td style="width: 400px; height: 150px;">
                                        <textarea name="content" style="resize: none; height: 150px; width: 100%; border: none; outline: none; padding: 10px; font-size: 16px"></textarea>
                                    </td>
                                </tr>
                            </table>
                            <div class="makeReviewButtonDiv">
                                <input type="hidden" name="oid" value="${order.id}">
                                <button type="submit" class="btn btn-info" style="height: 30px; margin-top: 8px; padding-top: 2px; font-size: 16px">确认评价</button>
                            </div>
                        </form>
                    </div>
                </div>
                <div style="clear:both"></div>
            </div>
        </div>
    </div>
</article>