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
            /* ?????????????????? */
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

            /* ????????????????????????????????? */
            // ???????????????
            String method = (String) request.getAttribute("method");
            // ????????????
            Method m = this.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class, PageBean.class);
            // ????????????????????????????????????
            String redirect = m.invoke(this, request, response, page).toString();

            /* ????????????????????????????????????????????????????????????????????????????????????????????????????????? */
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
     * ?????????????????????????????????params????????????????????????????????????
     *
     * @param: params
     * @param: request
     * @return: InputStream
     */
    public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
        InputStream inputStream = null;

        try {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            // ??????????????????????????????10MB????????????B?????????1 * 1024 * 1024 * 10
            diskFileItemFactory.setSizeThreshold(1024 * 10240);
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            for (FileItem fileItem : fileItems) {
                // ?????????????????????????????????????????????????????????true????????????????????????????????????
                if (fileItem.isFormField()) {
                    String paramName = fileItem.getFieldName();
                    String paramValue = fileItem.getString();
                    // ??????????????????????????????UTF-8
                    paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                    params.put(paramName, paramValue);
                } else {
                    // ??????????????????????????????
                    inputStream = fileItem.getInputStream();
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }
}
