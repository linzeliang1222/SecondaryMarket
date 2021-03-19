package top.linzeliang.web.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import top.linzeliang.domain.PageBean;
import top.linzeliang.service.*;
import top.linzeliang.service.impl.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public abstract class BaseBackServlet extends HttpServlet {

    protected UserService userService = new UserServiceImpl();
    protected CategoryService categoryService = new CategoryServiceImpl();
    protected ProductService productService = new ProductServiceImpl();
    protected ProductImageService productImageService = new ProductImageServiceImpl();
    protected ReviewService reviewService = new ReviewServiceImpl();
    protected OrderService orderService = new OrderServiceImpl();
    protected OrderItemService orderItemService = new OrderItemServiceImpl();
    protected AdministratorUserService adminUserService = new AdministratorUserServiceImpl();

    public abstract String add(HttpServletRequest request, HttpServletResponse response, PageBean page);
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, PageBean page);
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, PageBean page);
    public abstract String update(HttpServletRequest request, HttpServletResponse response, PageBean page);
    public abstract String list(HttpServletRequest request, HttpServletResponse response, PageBean page);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            /* 获取分页信息 */
            int start = 0;
            int count = 5;
            try {
                start = Integer.parseInt(request.getParameter("page.start"));
            } catch (Exception e) {

            }
            try {
                count = Integer.parseInt(request.getParameter("page.count"));
            } catch (Exception e) {

            }
            PageBean page = new PageBean(start, count);

            /* 借助反射，调用对应方法 */
            // 获取方法名
            String method = (String) request.getAttribute("method");
            // 获取方法
            Method m = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class, PageBean.class);
            // 执行方法，获取重定向路径
            String redirect = m.invoke(this, request, response, page).toString();

            /* 根据方法的返回值，进行相应的客户端跳转、服务端跳转、或者仅仅输出字符串 */
            if (redirect.startsWith("@")) {
                response.sendRedirect(redirect.substring(1));
            } else if (redirect.startsWith("%")) {
                response.getWriter().print(redirect.substring(1));
            } else {
                request.getRequestDispatcher(redirect).forward(request, response);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析请求的参数，存储到params中，然后返回请求的输入流
     *
     * @param: params
     * @param: request
     * @return: InputStream
     */
    public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
        InputStream inputStream = null;

        try {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            // 设置文件的上传大小为10MB，单位是B，所以1 * 1024 * 1024 * 10
            diskFileItemFactory.setSizeThreshold(1024 * 10240);
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            for (FileItem fileItem : fileItems) {
                // 判断是否是常规字段还是提交的文件，返回true的时候，就表示是常规字段
                if (fileItem.isFormField()) {
                    String paramName = fileItem.getFieldName();
                    String paramValue = fileItem.getString();
                    // 转换字符的编码方式为UTF-8
                    paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                    params.put(paramName, paramValue);
                } else {
                    // 获取上传文件的输入流
                    inputStream = fileItem.getInputStream();
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }
}
