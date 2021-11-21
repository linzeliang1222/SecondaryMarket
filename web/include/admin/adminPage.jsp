<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>

<script>
    $(function () {
        // 让class为disabled的a标签点了没反应
        $("ul.pagination li.disabled a").click(function () {
            return false;
        });
    });
</script>

<div>
    <ul class="pagination">
        <li <c:if test="${page.previous}">class="disabled"</c:if>>
            <a href="?page.start=0${page.param}" aria-label="Previous">
                <span aria-hidden="true">«</span>
            </a>
        </li>

        <li <c:if test="${page.previous}"> class="disabled"</c:if>>
            <a href="?page.start=${page.start - page.count}${page.param}" aria-label="Previous">
                <span aria-hidden="true">‹</span>
            </a>
        </li>

        <c:forEach begin="0" end="${page.totalPage - 1}" varStatus="status">
            <c:if test="${status.count * page.count - page.start <= 20 && status.count * page.count - page.start >= -10}">
                <li <c:if test="${status.index * page.count == page.start}">class="disabled"</c:if>>
                    <a href="?page.start=${status.index * page.count}${page.param}"
                       <c:if test="${status.index * page.count == page.start}">class="current"</c:if>>
                            ${status.count}
                    </a>
                </li>
            </c:if>
        </c:forEach>

        <li <c:if test="${page.end}">class="disabled"</c:if>>
            <a href="?page.start=${page.start + page.count}${page.param}" aria-label="Next">
                <span aria-hidden="true">›</span>
            </a>
        </li>

        <li <c:if test="${page.end}">class="disabled"</c:if>>
            <a href="?page.start=${page.last}${page.param}" aria-label="Next">
                <span aria-hidden="true">»</span>
            </a>
        </li>
    </ul>
</div>

