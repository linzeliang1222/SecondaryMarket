package top.linzeliang.web.filter;


import org.apache.commons.lang3.StringUtils;
import top.linzeliang.domain.Category;
import top.linzeliang.domain.User;
import top.linzeliang.service.CategoryService;
import top.linzeliang.service.impl.CategoryServiceImpl;
import top.linzeliang.service.impl.OrderItemServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ForeServletFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        User user = (User) request.getSession().getAttribute("user");
        int totalCountCollect = 0;
        if (null != user) {
            totalCountCollect = new OrderItemServiceImpl().getTotalOrderItemByUser(user.getId());
        }
        request.setAttribute("totalCountCollect", totalCountCollect);


        // 获取分类
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        if (null == categories) {
            categories = new CategoryServiceImpl().findAllCategory();
            request.setAttribute("categories", categories);
        }

        // 解析URL，获取请求方法
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        request.setAttribute("contextPath", contextPath);
        // 将根路径从uri中移除
        requestURI = StringUtils.remove(requestURI, contextPath);
        if (requestURI.startsWith("/fore") && !requestURI.startsWith("/foreServlet")) {
            String method = StringUtils.substringAfter(requestURI, "/fore");
            request.setAttribute("method", method);
            request.getRequestDispatcher("foreServlet").forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
