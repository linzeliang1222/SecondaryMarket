<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {
        $(".logout").click(function () {
            var urlPage = "forelogout";
            $.get(
                urlPage,
                function (result) {
                    if ("success" == result) {
                        location.reload();
                    }
                }
            );
        });
    });
</script>

<!-- 顶部导航S -->
<nav>
    <div class="container">
        <div class="lt">
            <a class="logo" href="${contextPath}">闲置物品交易市场</a>
            <form action="foresearch" method="get">
                <input class="sousuo form-control" placeholder="请输入关键字" type="text" name="keyword" style="color: black; padding-left: 10px">
                <button class="sousuo-btn btn btn-outline-secondary">搜索</button>
            </form>
        </div>

        <c:if test="${!empty user}">
            <div class="gt">
                <span><a href="foreaddProduct" title="发布商品" style="font-size: 24px; font-weight: bold">+</a></span>
                <div class="list">
                    <span>你好，
                        <c:if test="${fn:length(user.name) > 6}">${fn:substring(user.name, 0, 5)}...</c:if>
                        <c:if test="${fn:length(user.name) <= 6}">${user.name}</c:if>
                    </span>
                    <ul>
                        <li><a href="forecollect">我的收藏(${totalCountCollect})</a></li>
                        <li><a href="foreorder">我的订单</a></li>
                        <li><a href="foreuser">个人中心</a></li>
                        <li><a href="forerelease">我发布的</a></li>
                    </ul>
                </div>
                <a href="javascript:void(0)" class="in logout">注销</a>
            </div>
        </c:if>

        <c:if test="${empty user}">
            <div class="gt">
                <a href="login.jsp">登录</a>
                <span>|</span>
                <a href="register.jsp">注册</a>
            </div>
        </c:if>
    </div>
</nav>
<!-- 顶部导航E -->