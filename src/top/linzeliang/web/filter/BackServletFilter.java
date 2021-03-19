package top.linzeliang.web.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BackServletFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 先转换成HttpServletRequest和HttpServletResponse
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 获取根路径
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        // 将根路径从uri中移除
        requestURI = StringUtils.remove(requestURI, contextPath);
        // 只过滤/admin开头的，其他的图片，资源不过滤
        if (requestURI.startsWith("/admin-")) {
            requestURI = StringUtils.remove(requestURI, "/admin-");
            // 资源路径
            String servletPath = StringUtils.substringBefore(requestURI, "-") + "Servlet";
            // 调用的方法名
            String method = StringUtils.substringAfter(requestURI, "-");
            request.setAttribute("method", method);
            // 内部资源转发
            request.getRequestDispatcher(servletPath).forward(request, response);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
