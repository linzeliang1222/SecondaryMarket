<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>

<script>
    $(function () {
        // 让class为disabled和active的a标签点了没反应
        $("li.active a, li.disabled a").click(function () {
            return false;
        });
    });
</script>

<div style="display: inline-block; margin-top: 50px;">

    <div aria-label="..." style="margin: 0 auto">
        <ul class="pagination">
            <li class="page-item <c:if test="${page.previous}">disabled</c:if>">
                <a href="?page.start=0${page.param}" aria-label="Previous">
                    <span class="page-link">«</span>
                </a>
            </li>

            <li class="page-item <c:if test="${page.previous}">disabled</c:if>">
                <a href="?page.start=${page.start - page.count}${page.param}" aria-label="Previous">
                    <span class="page-link">‹</span>
                </a>
            </li>

            <c:forEach begin="0" end="${page.totalPage - 1}" varStatus="status">
                <c:if test="${status.count * page.count - page.start <= 80 && status.count * page.count - page.start >= -40}">
                    <li class="page-item <c:if test="${status.index * page.count == page.start}">active</c:if>">
                        <a href="?page.start=${status.index * page.count}${page.param}">
                            <span class="page-link">${status.count}</span>
                        </a>
                    </li>
                </c:if>
            </c:forEach>

            <li class="page-item <c:if test="${page.end}">disabled</c:if>">
                <a href="?page.start=${page.start + page.count}${page.param}" aria-label="Next">
                    <span class="page-link">›</span>
                </a>
            </li>

            <li class="page-item <c:if test="${page.end}">disabled</c:if>">
                <a href="?page.start=${page.last}${page.param}" aria-label="Next">
                    <span class="page-link">»</span>
                </a>
            </li>
        </ul>
    </div>
</div>